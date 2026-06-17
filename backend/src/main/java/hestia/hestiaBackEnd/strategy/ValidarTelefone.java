package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import hestia.hestiaBackEnd.domain.Telefone;
import org.springframework.stereotype.Component;

/**
 * Valida o formato do telefone do hóspede.
 */
@Component
public class ValidarTelefone implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para validação de telefone.";
        }
        Telefone telefone = hospede.getTelefone();
        if (telefone == null) {
            return "Telefone é obrigatório.";
        }
        if (telefone.getDdd() == null || !telefone.getDdd().matches("\\d{2}")) {
            return "DDD inválido.";
        }
        if (telefone.getNumero() == null || !telefone.getNumero().replaceAll("\\D", "").matches("\\d{8,9}")) {
            return "Número de telefone inválido.";
        }
        return null;
    }
}
