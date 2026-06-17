package hestia.hestiaBackEnd.dao;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO do hóspede implementado sobre JPA/Hibernate (EntityManager). A persistência fica encapsulada
 * atrás de {@link IDAO}, de modo que Fachada, strategies e controllers não conhecem a tecnologia.
 * As transações são controladas pela Fachada (@Transactional).
 */
@Repository
public class HospedeDAO implements IDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void salvar(EntidadeDominio entidade) {
        Hospede hospede = (Hospede) entidade;
        // persist torna a entidade gerenciada: o id (IDENTITY) e as datas de auditoria
        // ficam preenchidos diretamente no objeto recebido (cascade para endereco/telefone).
        em.persist(hospede);
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Hospede hospede = (Hospede) entidade;
        Hospede gerenciado = em.merge(hospede);
        em.flush();
        // reflete de volta os dados persistidos para o objeto recebido (usado na resposta).
        hospede.setId(gerenciado.getId());
        hospede.setDtCadastro(gerenciado.getDtCadastro());
        hospede.setDtAlteracao(gerenciado.getDtAlteracao());
    }

    @Override
    public void excluir(EntidadeDominio entidade) {
        Hospede hospede = (Hospede) entidade;
        Hospede gerenciado = em.merge(hospede);
        em.flush();
        hospede.setDtAlteracao(gerenciado.getDtAlteracao());
    }

    @Override
    public EntidadeDominio[] consultar(EntidadeDominio entidade) {
        Hospede filtro = (entidade instanceof Hospede) ? (Hospede) entidade : null;

        StringBuilder jpql = new StringBuilder("SELECT h FROM Hospede h WHERE 1 = 1");
        boolean porId = filtro != null && filtro.getId() != null;
        boolean porCpf = !porId && filtro != null && filtro.getCpf() != null;

        if (porId) {
            jpql.append(" AND h.id = :id");
        } else if (porCpf) {
            jpql.append(" AND h.cpf = :cpf");
        }

        TypedQuery<Hospede> query = em.createQuery(jpql.toString(), Hospede.class);
        if (porId) {
            query.setParameter("id", filtro.getId());
        } else if (porCpf) {
            query.setParameter("cpf", filtro.getCpf());
        }

        List<Hospede> resultado = query.getResultList();
        return resultado.toArray(new EntidadeDominio[0]);
    }
}
