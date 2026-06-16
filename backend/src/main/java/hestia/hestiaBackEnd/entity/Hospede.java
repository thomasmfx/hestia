package hestia.hestiaBackEnd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hospedes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Hospede {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 60)
    String nome;

    @Column(nullable = false, unique = true, length = 11)
    String cpf;

    @Column(nullable = false)
    LocalDate dataNascimento;

    @Column(nullable = false, length = 2)
    String ddd;

    @Column(nullable = false, length = 15)
    String telefone;

    @Column(nullable = false, length = 254)
    String email;

    @Column(nullable = false, length = 60)
    String senha;

    @Column(nullable = false, length = 40)
    String logradouro;

    @Column(nullable = false, length = 10)
    String numero;

    @Column(nullable = false, length = 8)
    String cep;

    @Column(nullable = false, length = 30)
    String bairro;

    @Column(nullable = true, length = 200)
    String complemento;

    @Column(nullable = false, length = 20)
    String cidade;

    @Column(nullable = false, length = 2)
    String estado;

    @Column(nullable = false)
    @CreatedDate
    LocalDateTime dataRegistro;

    @Column(nullable = false)
    @LastModifiedDate
    LocalDateTime ultimaAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    StatusHospede statusAtividade;

    @Column(nullable = false)
    LocalDateTime dataAceiteTermos;

    @Column(nullable = false)
    Boolean aceiteTermos;
}