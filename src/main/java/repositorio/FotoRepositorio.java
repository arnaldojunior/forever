package repositorio;

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
import modelo.Foto;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class FotoRepositorio {
    
    @PersistenceContext(unitName = "ForeverPU")
    private EntityManager em;
    
    @Resource
    private UserTransaction transaction;
    
    public void create(Foto foto) {
        try {
            transaction.begin();
            em.persist(foto);
            transaction.commit();
            
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FotoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
