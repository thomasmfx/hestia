package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidarEmail implements IStrategy {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para validação de e-mail.";
        }
        String email = hospede.getEmail();
        if (email == null || !EMAIL.matcher(email).matches()) {
            return "E-mail em formato inválido.";
        }
        return null;
    }
}
