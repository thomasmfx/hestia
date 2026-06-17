package hestia.hestiaBackEnd.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AtualizarHospedeRequest {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dtNascimento;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @Valid
    @NotNull(message = "Os dados de telefone são obrigatórios")
    private TelefoneRequest telefone;

    @Valid
    @NotNull(message = "Os dados de endereço são obrigatórios")
    private EnderecoRequest endereco;
}