package hestia.hestiaBackEnd.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telefone")
@Getter
@Setter
public class Telefone extends EntidadeDominio {

    @Column(nullable = false, length = 2)
    String ddd;

    @Column(nullable = false, length = 15)
    String numero;
}
