package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;

public interface IStrategy {
    String processar(EntidadeDominio entidade);
}
