package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.dao.HospedeDAO;
import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import org.springframework.stereotype.Component;

/**
 * RN0202 / RN0211 - valida o formato/dígitos verificadores do CPF e garante a unicidade.
 */
@Component
public class ValidarCPF implements IStrategy {

    public static final String CPF_JA_CADASTRADO = "CPF já cadastrado no sistema.";

    private final HospedeDAO hospedeDAO;

    public ValidarCPF(HospedeDAO hospedeDAO) {
        this.hospedeDAO = hospedeDAO;
    }

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para validação de CPF.";
        }
        if (!cpfValido(hospede.getCpf())) {
            return "CPF inválido.";
        }

        Hospede filtro = new Hospede();
        filtro.setCpf(hospede.getCpf());
        EntidadeDominio[] existentes = hospedeDAO.consultar(filtro);
        for (EntidadeDominio existente : existentes) {
            Hospede outro = (Hospede) existente;
            if (!outro.getId().equals(hospede.getId())) {
                return CPF_JA_CADASTRADO;
            }
        }
        return null;
    }

    private boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.length() != 11 || digitos.chars().distinct().count() == 1) {
            return false;
        }
        return verificarDigito(digitos, 9) && verificarDigito(digitos, 10);
    }

    private boolean verificarDigito(String cpf, int posicao) {
        int soma = 0;
        int peso = posicao + 1;
        for (int i = 0; i < posicao; i++) {
            soma += (cpf.charAt(i) - '0') * (peso - i);
        }
        int resto = soma % 11;
        int digitoEsperado = (resto < 2) ? 0 : 11 - resto;
        return digitoEsperado == (cpf.charAt(posicao) - '0');
    }
}
