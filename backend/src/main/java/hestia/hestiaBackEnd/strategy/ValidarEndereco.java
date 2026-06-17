package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.Endereco;
import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import org.springframework.stereotype.Component;

@Component
public class ValidarEndereco implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para validação de endereço.";
        }
        Endereco endereco = hospede.getEndereco();
        if (endereco == null) {
            return "Endereço é obrigatório.";
        }
        if (isVazio(endereco.getLogradouro())) {
            return "Logradouro é obrigatório.";
        }
        if (isVazio(endereco.getNumero())) {
            return "Número do endereço é obrigatório.";
        }
        if (isVazio(endereco.getCep()) || !endereco.getCep().replaceAll("\\D", "").matches("\\d{8}")) {
            return "CEP inválido.";
        }
        if (isVazio(endereco.getBairro())) {
            return "Bairro é obrigatório.";
        }
        if (isVazio(endereco.getCidade())) {
            return "Cidade é obrigatória.";
        }
        if (isVazio(endereco.getEstado()) || endereco.getEstado().length() != 2) {
            return "Estado inválido.";
        }
        return null;
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
