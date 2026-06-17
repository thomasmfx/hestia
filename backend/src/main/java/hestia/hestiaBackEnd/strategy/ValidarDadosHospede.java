package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import org.springframework.stereotype.Component;

@Component
public class ValidarDadosHospede implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para validação de hóspede.";
        }
        if (isVazio(hospede.getNome())) {
            return "Nome é obrigatório.";
        }
        if (hospede.getDtNascimento() == null) {
            return "Data de nascimento é obrigatória.";
        }
        if (isVazio(hospede.getEmail())) {
            return "E-mail é obrigatório.";
        }
        if (hospede.getEndereco() == null) {
            return "Endereço é obrigatório.";
        }
        if (hospede.getTelefone() == null) {
            return "Telefone é obrigatório.";
        }
        if (!hospede.isAceitouTermos()) {
            return "É necessário aceitar os termos de uso.";
        }
        return null;
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
