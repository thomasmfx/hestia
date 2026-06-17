package hestia.hestiaBackEnd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Dados alteráveis do hóspede. O CPF e a senha não podem ser alterados por este fluxo (RN0202).
 */
@Getter
@Setter
public class AlterarHospedeRequest {
    @NotBlank
    @Size(min = 2, max = 60)
    String nome;

    @NotNull
    LocalDate dtNascimento;

    @Email
    @NotBlank
    String email;

    @NotNull
    TelefoneRequest telefone;

    @NotNull
    EnderecoRequest endereco;
}
