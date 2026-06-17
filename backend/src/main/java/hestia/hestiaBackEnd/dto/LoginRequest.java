package hestia.hestiaBackEnd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    String cpf;

    @NotBlank
    String senha;
}
