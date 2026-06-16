package hestia.hestiaBackEnd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CadastrarHospedeRequest {
    @NotBlank
    @Size(min = 2, max = 60)
    String nome;

    @NotBlank
    @Size(min = 11, max = 11)
    String cpf;

    @NotNull
    LocalDate dtNascimento;

    @Email
    @NotBlank
    String email;

    @NotBlank
    @Size(min = 8, max = 60)
    String senha;

    @NotNull
    Boolean aceitouTermos;

    @NotNull
    TelefoneRequest telefone;

    @NotNull
    EnderecoRequest endereco;
}
