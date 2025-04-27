package boxshop.services;

import boxshop.exception.EmailNaoCriadoException;
import boxshop.repository.LoginRepository;
import boxshop.repository.LogistaRepository;
import jakarta.transaction.Transactional;
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
}
