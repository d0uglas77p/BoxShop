package boxshop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Logista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogista;

    @Column(nullable = false)
    private String nomeCompletoLogista;

    @Column(nullable = false, unique = true)
    private String cpfLogista;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimentoLogista;

    @Column(nullable = false)
    private String generoLogista;

    @Column(nullable = false, unique = true)
    private String nomeComercioLogista;

    @Column(nullable = false, unique = true)
    private String emailComercioLogista;

    @Column(nullable = false, unique = true)
    private String whatsappComercioLogista;

    @Column(nullable = false)
    private String tipoComercioLogista;

    @Column(nullable = false)
    private String senhaComercialLogista;

    @Column(updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    private LocalDate dataRegistroComercialLogista;

    private String tokenRecuperacao;

    private LocalDateTime tokenExpiracao;


    public Long getIdLogista() {
        return idLogista;
    }

    public void setIdLogista(Long idLogista) {
        this.idLogista = idLogista;
    }

    public String getNomeCompletoLogista() {
        return nomeCompletoLogista;
    }

    public void setNomeCompletoLogista(String nomeCompletoLogista) {
        this.nomeCompletoLogista = nomeCompletoLogista;
    }

    public String getCpfLogista() {
        return cpfLogista;
    }

    public void setCpfLogista(String cpfLogista) {
        this.cpfLogista = cpfLogista;
    }

    public LocalDate getDataNascimentoLogista() {
        return dataNascimentoLogista;
    }

    public void setDataNascimentoLogista(LocalDate dataNascimentoLogista) {
        this.dataNascimentoLogista = dataNascimentoLogista;
    }

    public String getGeneroLogista() {
        return generoLogista;
    }

    public void setGeneroLogista(String generoLogista) {
        this.generoLogista = generoLogista;
    }

    public String getNomeComercioLogista() {
        return nomeComercioLogista;
    }

    public void setNomeComercioLogista(String nomeComercioLogista) {
        this.nomeComercioLogista = nomeComercioLogista;
    }

    public String getEmailComercioLogista() {
        return emailComercioLogista;
    }

    public void setEmailComercioLogista(String emailComercioLogista) {
        this.emailComercioLogista = emailComercioLogista;
    }

    public String getWhatsappComercioLogista() {
        return whatsappComercioLogista;
    }

    public void setWhatsappComercioLogista(String whatsappComercioLogista) {
        this.whatsappComercioLogista = whatsappComercioLogista;
    }

    public String getTipoComercioLogista() {
        return tipoComercioLogista;
    }

    public void setTipoComercioLogista(String tipoComercioLogista) {
        this.tipoComercioLogista = tipoComercioLogista;
    }

    public String getSenhaComercialLogista() {
        return senhaComercialLogista;
    }

    public void setSenhaComercialLogista(String senhaComercialLogista) {
        this.senhaComercialLogista = senhaComercialLogista;
    }

    public LocalDate getDataRegistroComercialLogista() {
        return dataRegistroComercialLogista;
    }

    public void setDataRegistroComercialLogista(LocalDate dataRegistroComercialLogista) {
        this.dataRegistroComercialLogista = dataRegistroComercialLogista;
    }

    public String getTokenRecuperacao() {
        return tokenRecuperacao;
    }

    public void setTokenRecuperacao(String tokenRecuperacao) {
        this.tokenRecuperacao = tokenRecuperacao;
    }

    public LocalDateTime getTokenExpiracao() {
        return tokenExpiracao;
    }

    public void setTokenExpiracao(LocalDateTime tokenExpiracao) {
        this.tokenExpiracao = tokenExpiracao;
    }
}