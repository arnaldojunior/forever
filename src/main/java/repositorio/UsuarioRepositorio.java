package repositorio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Usuario;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class UsuarioRepositorio {
    
    /**
     * O ciclo de vida do EntityManager é gerenciado pela aplicação. Cada EM
     * possui um contexto de persistência isolado. O EM precisa ser criado e 
     * destruído explicitamente pela aplicação.
     */
    @PersistenceUnit(unitName = "ForeverPU")
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction transaction;
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            transaction.begin();
            em = this.getEntityManager();
            em.persist(usuario);
            transaction.commit();
            
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
