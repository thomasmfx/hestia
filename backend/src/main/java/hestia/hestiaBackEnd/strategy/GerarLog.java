package hestia.hestiaBackEnd.strategy;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GerarLog implements IStrategy {

    private static final Logger log = LoggerFactory.getLogger(GerarLog.class);

    @Override
    public String processar(EntidadeDominio entidade) {
        log.info("[AUDITORIA] operação concluída para entidade={} id={} dtAlteracao={}",
                entidade.getClass().getSimpleName(), entidade.getId(), entidade.getDtAlteracao());
        return null;
    }
}
