package hestia.hestiaBackEnd.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "hospede")
@Getter
@Setter
public class Hospede extends EntidadeDominio {

    @Column(nullable = false)
    boolean ativo;

    @Column(nullable = false, length = 60)
    String nome;

    @Column(nullable = false, unique = true, length = 11)
    String cpf;

    @Column(nullable = false)
    LocalDate dtNascimento;

    @Column(nullable = false, length = 254)
    String email;

    @Column(nullable = false, length = 60)
    String senha;

    @Column(nullable = false)
    boolean aceitouTermos;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", nullable = false)
    Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "telefone_id", nullable = false)
    Telefone telefone;
}
