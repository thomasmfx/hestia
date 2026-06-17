package hestia.hestiaBackEnd.fachada;

import hestia.hestiaBackEnd.dao.HospedeDAO;
import hestia.hestiaBackEnd.dao.IDAO;
import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import hestia.hestiaBackEnd.strategy.CriptografarSenha;
import hestia.hestiaBackEnd.strategy.GerarLog;
import hestia.hestiaBackEnd.strategy.IStrategy;
import hestia.hestiaBackEnd.strategy.ValidarCPF;
import hestia.hestiaBackEnd.strategy.ValidarDadosHospede;
import hestia.hestiaBackEnd.strategy.ValidarEmail;
import hestia.hestiaBackEnd.strategy.ValidarEndereco;
import hestia.hestiaBackEnd.strategy.ValidarTelefone;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Ponto central da aplicação: recebe uma {@link EntidadeDominio}, identifica o tipo, executa as
 * strategies pertinentes (em ordem, parando no primeiro erro), delega ao {@link IDAO} correspondente
 * e, ao final, registra o log de auditoria. Beans concretos são injetados (evita ambiguidade entre
 * múltiplos beans de IDAO/IStrategy) e as listas/mapas são montados no construtor.
 */
@Component
public class Fachada implements IFachada {

    private static final String SALVAR = "salvar";
    private static final String ALTERAR = "alterar";
    private static final String EXCLUIR = "excluir";

    private final Map<String, IDAO> daos = new HashMap<>();
    private final Map<String, Map<String, List<IStrategy>>> regras = new HashMap<>();
    private final GerarLog gerarLog;

    public Fachada(HospedeDAO hospedeDAO,
                   ValidarDadosHospede validarDadosHospede,
                   ValidarCPF validarCPF,
                   ValidarEmail validarEmail,
                   ValidarEndereco validarEndereco,
                   ValidarTelefone validarTelefone,
                   CriptografarSenha criptografarSenha,
                   GerarLog gerarLog) {
        this.gerarLog = gerarLog;

        String hospede = Hospede.class.getSimpleName();

        daos.put(hospede, hospedeDAO);

        Map<String, List<IStrategy>> operacoesHospede = new HashMap<>();
        operacoesHospede.put(SALVAR, List.of(
                validarDadosHospede, validarCPF, validarEmail,
                validarEndereco, validarTelefone, criptografarSenha));
        operacoesHospede.put(ALTERAR, List.of(
                validarDadosHospede, validarEmail, validarEndereco, validarTelefone));
        regras.put(hospede, operacoesHospede);
    }

    @Override
    @Transactional
    public String salvar(EntidadeDominio entidade) {
        return executar(entidade, SALVAR, IDAO::salvar);
    }

    @Override
    @Transactional
    public String alterar(EntidadeDominio entidade) {
        return executar(entidade, ALTERAR, IDAO::alterar);
    }

    @Override
    @Transactional
    public String excluir(EntidadeDominio entidade) {
        return executar(entidade, EXCLUIR, IDAO::excluir);
    }

    @Override
    @Transactional(readOnly = true)
    public EntidadeDominio[] consultar(EntidadeDominio entidade) {
        IDAO dao = daos.get(entidade.getClass().getSimpleName());
        if (dao == null) {
            return new EntidadeDominio[0];
        }
        return dao.consultar(entidade);
    }

    private String executar(EntidadeDominio entidade, String operacao,
                            BiConsumer<IDAO, EntidadeDominio> persistir) {
        String nome = entidade.getClass().getSimpleName();
        IDAO dao = daos.get(nome);
        if (dao == null) {
            return "Entidade não suportada: " + nome;
        }

        List<IStrategy> estrategias = regras
                .getOrDefault(nome, Map.of())
                .getOrDefault(operacao, List.of());

        for (IStrategy estrategia : estrategias) {
            String erro = estrategia.processar(entidade);
            if (erro != null) {
                return erro;
            }
        }

        persistir.accept(dao, entidade);
        gerarLog.processar(entidade);
        return null;
    }
}
