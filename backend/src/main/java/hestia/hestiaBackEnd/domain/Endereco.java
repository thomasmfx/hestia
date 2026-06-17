package hestia.hestiaBackEnd.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class Endereco extends EntidadeDominio {

    @Column(nullable = false, length = 40)
    String logradouro;

    @Column(nullable = false, length = 10)
    String numero;

    @Column(nullable = false, length = 8)
    String cep;

    @Column(length = 200)
    String complemento;

    @Column(nullable = false, length = 30)
    String bairro;

    @Column(nullable = false, length = 20)
    String cidade;

    @Column(nullable = false, length = 2)
    String estado;
}
