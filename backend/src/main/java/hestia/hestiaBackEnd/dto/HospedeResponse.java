package hestia.hestiaBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospedeResponse {
    UUID id;
    String nome;
    String cpf;
    LocalDate dtaNascimento;
    String email;
    TelefoneResponse telefone;
    EnderecoResponse endereco;
}
