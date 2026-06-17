package hestia.hestiaBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErroValidacaoResponse {
    int status;
    String erro;
    String mensagem;
    List<Campo> campos;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Campo {
        String campo;
        String mensagem;
    }
}
