package hestia.hestiaBackEnd.fachada;

import hestia.hestiaBackEnd.domain.EntidadeDominio;

public interface IFachada {
    String salvar(EntidadeDominio entidade);
    String alterar(EntidadeDominio entidade);
    String excluir(EntidadeDominio entidade);
    EntidadeDominio[] consultar(EntidadeDominio entidade);
}
