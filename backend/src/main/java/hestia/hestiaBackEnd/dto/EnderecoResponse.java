package hestia.hestiaBackEnd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponse {
    String logradouro;
    String numero;
    String complemento;
    String bairro;
    String cep;
    String cidade;
    String estado;
}
