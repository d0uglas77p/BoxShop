package boxshop.services;

import boxshop.exception.CpfJaCriadoException;
import boxshop.exception.EmailComercioJaCriadoException;
import boxshop.exception.WhatsappComercioJaCriadoException;
import boxshop.model.Logista;
import boxshop.repository.LogistaRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogistaService {
    @Autowired
    private LogistaRepository logistaRepository;

    public Logista criarContaLogista(Logista logista) {
        if (logistaRepository.existsByCpfLogista(logista.getCpfLogista())) {
            throw new CpfJaCriadoException("CPF já cadastrado!");
        }

        if (logistaRepository.existsByEmailComercioLogista(logista.getEmailComercioLogista())) {
            throw new EmailComercioJaCriadoException("E-mail já cadastrado!");
        }

        if (logistaRepository.existsByWhatsappComercioLogista(logista.getWhatsappComercioLogista())) {
            throw new WhatsappComercioJaCriadoException("WhatsApp já cadastrado!");
        }

        String senhaCriptografada = BCrypt.hashpw(logista.getSenhaComercialLogista(), BCrypt.gensalt());
        logista.setSenhaComercialLogista(senhaCriptografada);
        return logistaRepository.save(logista);
    }

    public boolean verificarNomeComercioExistente(String nomeComercioLogista) {
        return logistaRepository.existsByNomeComercioLogista(nomeComercioLogista);
    }
}