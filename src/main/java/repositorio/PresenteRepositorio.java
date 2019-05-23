package repositorio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Categoria;
import modelo.Presente;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class PresenteRepositorio {

    /**
     * O EntityManager tem seu ciclo de vida gerenciado pelo container Java EE.
     * O Contexto de Persistência é propagado pelo container para todos os
     * componentes da aplicação que utilizam a instância EntityManager dentro de
     * uma transação JTA (Java Transaction Architecture).
     */
    @PersistenceContext(unitName = "ForeverPU")
    private EntityManager em;

    @Resource
    private UserTransaction transacao;

    public void criar(Presente presente) {
        try {
            transacao.begin();
            Categoria categoria = em.find(Categoria.class, presente.getCategoria().getId());
            categoria.addPresente(presente);
            em.persist(presente);
            transacao.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }

    public void deletar(Long id) {
        try {
            transacao.begin();
            Presente presente;
            presente = em.getReference(Presente.class, id);
            em.remove(presente);
            transacao.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(PresenteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Presente> buscarTodos() {
        return em.createQuery("SELECT p FROM Presente p").getResultList();
    }

    public List<Presente> buscarPorCategoria(String categoria) {
        return em.createQuery("SELECT p FROM Presente p WHERE p.categoria.nome = ?1").
                setParameter(1, categoria).getResultList();
    }

    public List<Presente> buscarPorCategoria(Categoria categoria) {
        return em.createNamedQuery("Presente.presentesPorCategoria").
                setParameter("categoria", categoria).getResultList();
    }
    
    /**
     * Retorna os quatro presentes mais caros.
     * @return 
     */
    public List<Presente> buscarPresentesMaisCaros() {
        return em.createNamedQuery("Presente.presentesMaisCaros").
                setMaxResults(4).getResultList();
    }

    /**
     * Busca os presentes que custam mais que o valor especificado.
     * @param valor
     * @return 
     */
    public List<Presente> buscarPresentesMaisCarosQue(Double valor) {
        return em.createNamedQuery("Presente.valorMinimo").
                setParameter("valor", valor).getResultList();
    }
    
    /**
     * Retorna o valor total dos presentes de uma categoria específica.
     * @param categoria
     * @return 
     */
    public List<Object[]> valorTotalDosPresentesDaCategoria(Categoria categoria) {
        return em.createQuery(
                "SELECT c.nome, SUM(p.valor) FROM Presente p "
                        + "INNER JOIN p.categoria c WHERE p.categoria = ?1 "
                        + "GROUP BY c.nome")
                .setParameter(1, categoria).getResultList();
    }
    
    /**
     * Retorna o valor total de todos os presentes para cada categoria.
     * @return 
     */
    public List<Object[]> valorTotalDosPresentesPorCategoria() {
        return em.createQuery(
                "SELECT c.nome, SUM(p.valor) FROM Presente p "
                        + "INNER JOIN p.categoria c GROUP BY c.nome")
                .getResultList();
    }
}
