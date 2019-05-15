package repositorio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Categoria;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class CategoriaRepositorio {

    @PersistenceContext(unitName = "ForeverPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Categoria categoria) {
        try {
            transaction.begin();
            em.persist(categoria);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }

    public void delete(Long id) {
        try {
            transaction.begin();
            Categoria categoria;
            categoria = em.getReference(Categoria.class, id);
            em.remove(categoria);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(CategoriaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Categoria> findAll() {
        return em.createQuery("SELECT c FROM Categoria c").getResultList();
    }
    
    public Categoria findById(Long id) {
        Categoria categoria = new Categoria();
        try {
            categoria = em.find(Categoria.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar categoria por id: "+ e);
        }
        return categoria;
    }
    
    public Categoria findByName(String nome) {
        Categoria categoria = new Categoria();
        try {
            categoria = (Categoria) em.createQuery("SELECT c FROM Categoria c WHERE c.nome = ?1").
                setParameter(1, nome).getSingleResult();
        } catch(NoResultException nre) {
            System.out.println("Erro ao buscar categoria por nome: "+ nre);
        }
        return categoria;
    }
}
