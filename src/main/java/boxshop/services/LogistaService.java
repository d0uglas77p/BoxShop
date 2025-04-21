package boxshop.services;

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
        String senhaCriptografada = BCrypt.hashpw(logista.getSenhaComercialLogista(), BCrypt.gensalt());
        logista.setSenhaComercialLogista(senhaCriptografada);
        return logistaRepository.save(logista);
    }
}