package boxshop.services;

import boxshop.exception.EmailNaoCriadoException;
import boxshop.exception.TokenExpiradoException;
import boxshop.model.Logista;
import boxshop.repository.LoginRepository;
import boxshop.repository.LogistaRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    LogistaRepository logistaRepository;
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean isEmailCadastrado(String emailComercioLogista){
        return logistaRepository.existsByEmailComercioLogista(emailComercioLogista);
    }

    @Transactional
    public String gerarTokenRecuperacao(String emailComercioLogista){
        String token = UUID.randomUUID().toString();
        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);
        loginRepository.savarTokenRecuperacao(emailComercioLogista, token, dataExpiracao);

        return token;
    }

    public void enviarEmailRecuperacao(String emailComercioLogista, String message){
        if(!isEmailCadastrado(emailComercioLogista)){
            throw new EmailNaoCriadoException("Email não cadastrado");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailComercioLogista);
        mailMessage.setSubject("Recuperação de senha");
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }
    public boolean isTokenValido(String token){
        LocalDateTime agora = LocalDateTime.now();
        Logista logista = loginRepository.findByTokenRecuperacao(token,agora);
        return logista !=null;
    }

    @Transactional
    public void atualizarSenha(String token, String novaSenhaComercialLogista){
        if(novaSenhaComercialLogista.length() < 8){
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres");
        }
        Logista logista = loginRepository.findByTokenRecuperacao(token,LocalDateTime.now());
        if(logista != null){
            logista.setSenhaComercialLogista(BCrypt.hashpw(novaSenhaComercialLogista,BCrypt.gensalt()));
            logista.setTokenRecuperacao(null);
            logista.setTokenExpiracao(null);
            logistaRepository.save(logista);
        }
        else{
            throw new TokenExpiradoException("Token expirado, tente novamente");
        }
    }
}
