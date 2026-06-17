package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriptografarSenha implements IStrategy {

    private final BCryptPasswordEncoder encoder;

    public CriptografarSenha(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String processar(EntidadeDominio entidade) {
        if (!(entidade instanceof Hospede hospede)) {
            return "Entidade inválida para criptografia de senha.";
        }
        String senha = hospede.getSenha();
        if (senha == null || senha.isBlank()) {
            return "Senha é obrigatória.";
        }
        hospede.setSenha(encoder.encode(senha));
        return null;
    }
}
