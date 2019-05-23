package controle;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Categoria;
import modelo.Presente;
import repositorio.CategoriaRepositorio;
import repositorio.PresenteRepositorio;

/**
 *
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class PresentesControle {

    @Inject
    private Presente presente;

    @Inject
    private PresenteRepositorio presenteRepositorio;

    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    List<Presente> presentes = new ArrayList<>();

    @PostConstruct
    public void inicializar() {
        System.out.println("POST CONSTRUCT");
        buscarTodosPresentes();
    }

    public Presente getPresente() {
        return presente;
    }

    public List<Presente> getPresentes() {
        return presentes;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepositorio.buscarTodos();
    }

    public void cadastrar() {
        String msg;
        try {
            presenteRepositorio.criar(presente);
            msg = "Presente " + presente.getDescricao() + " cadastrado com sucesso!";
            presente = new Presente();
        } catch (Exception e) {
            msg = "Erro ao cadastrar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long id) {
        String msg;
        Presente p;
        try {
            p = presenteRepositorio.deletar(id);
            System.out.println("DELETADO: "+ id);
            msg = "Presente "+ p.getDescricao() +" deletado com sucesso!";
            this.presentes = buscarTodosPresentes();
        } catch (Exception e) {
            msg = "Erro ao deletar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Presente> buscarTodosPresentes() {
        try {
            this.presentes = presenteRepositorio.buscarTodos();
        } catch (Exception e) {
            System.out.println("Erro ao buscar presentes!");
        }
        return presentes;
    }

    public void buscarPorCategoriaObjeto(ValueChangeEvent e) {
        try {
            Categoria cat = (Categoria) e.getNewValue();
            this.presentes = presenteRepositorio.buscarPorCategoria(cat);
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes por categoria: " + ex);
        }
    }

    public void buscarPresentesMaisCaros() {
        try {
            this.presentes = presenteRepositorio.buscarPresentesMaisCaros();
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes mais caros: " + ex);
        }
    }

    public void presentesQueCustamMaisQue() {
        try {
            // Busca os presentes que custam mais que o valor especificado.
            // Observo que este valor pode ser recebido por parâmetro.
            this.presentes = presenteRepositorio.buscarPresentesMaisCarosQue(
                    new Double(1800));
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes: " + ex);
        }
    }
}