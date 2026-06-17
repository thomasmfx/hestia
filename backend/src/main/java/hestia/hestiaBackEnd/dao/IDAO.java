package hestia.hestiaBackEnd.dao;

import hestia.hestiaBackEnd.domain.EntidadeDominio;

public interface IDAO {
    void salvar(EntidadeDominio entidade);
    void alterar(EntidadeDominio entidade);
    void excluir(EntidadeDominio entidade);
    EntidadeDominio[] consultar(EntidadeDominio entidade);
}
