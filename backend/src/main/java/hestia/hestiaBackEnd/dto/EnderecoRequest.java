package hestia.hestiaBackEnd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequest {
    String logradouro;
    String numero;
    String complemento;
    String bairro;
    String cep;
    String cidade;
    String estado;
}
