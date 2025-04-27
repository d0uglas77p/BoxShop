package boxshop.repository;

import boxshop.model.Logista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogistaRepository extends JpaRepository<Logista, Long> {

    boolean existsByCpfLogista(String cpfLogista);

    boolean existsByEmailComercioLogista(String emailComercioLogista);

    boolean existsByWhatsappComercioLogista(String whatsappComercioLogista);

    boolean existsByNomeComercioLogista(String nomeComercioLogista);
}