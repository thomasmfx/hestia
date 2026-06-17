package hestia.hestiaBackEnd.controller;

import hestia.hestiaBackEnd.dto.ErroResponse;
import hestia.hestiaBackEnd.dto.ErroValidacaoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Padroniza as respostas de erro conforme o contrato (ErroResponse / ErroValidacaoResponse).
 * Observação: falhas de autenticação (token ausente/ inválido) são tratadas pela cadeia de filtros
 * do Spring Security, antes de chegar aqui.
 */
@RestControllerAdvice
public class ManipuladorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoResponse> tratarValidacao(MethodArgumentNotValidException ex) {
        List<ErroValidacaoResponse.Campo> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> new ErroValidacaoResponse.Campo(erro.getField(), erro.getDefaultMessage()))
                .toList();
        ErroValidacaoResponse corpo = new ErroValidacaoResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Dados inválidos",
                "Um ou mais campos estão inválidos.",
                campos);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(corpo);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponse> tratarStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ErroResponse corpo = new ErroResponse(status.value(), status.getReasonPhrase(), ex.getReason());
        return ResponseEntity.status(status).body(corpo);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponse> tratarGenerico(RuntimeException ex) {
        ErroResponse corpo = new ErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpo);
    }
}
