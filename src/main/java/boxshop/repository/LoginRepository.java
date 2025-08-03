package boxshop.repository;

import boxshop.model.Logista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LoginRepository extends JpaRepository<Logista, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Logista u SET u.tokenRecuperacao = :token, u.tokenExpiracao = :tokenExpiracao WHERE u.emailComercioLogista = :emailComercioLogista")
    void savarTokenRecuperacao(
            @Param("emailComercioLogista") String emailComercioLogista,
            @Param("token") String token,
            @Param("tokenExpiracao") LocalDateTime tokenExpiracao
    );

    @Query("SELECT u FROM Logista u WHERE u.tokenRecuperacao = :token AND u.tokenExpiracao > :dataAtual")
    Logista findByTokenRecuperacao(
            @Param("token") String token,
            @Param("dataAtual") LocalDateTime dataAtual
    );
}