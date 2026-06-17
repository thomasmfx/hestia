package hestia.hestiaBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospedeResponse {
    Integer id;
    String nome;
    String cpf;
    LocalDate dtNascimento;
    String email;
    Boolean ativo;
    Boolean aceitouTermos;
    TelefoneResponse telefone;
    EnderecoResponse endereco;
    LocalDateTime dtCadastro;
    LocalDateTime dtAlteracao;
}
