package controle;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Categoria;
import repositorio.CategoriaRepositorio;

/**
 *
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class CategoriaControle {

    @Inject
    private Categoria categoria;
    
    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    public Categoria getCategoria() {
        return categoria;
    }

    public void cadastrar() {
        String msg;
        try {
            categoriaRepositorio.create(categoria);
            msg = "Categoria " + categoria.getNome() +" cadastrada com sucesso!";
            categoria = new Categoria();
        } catch (Exception e) {
            msg = "Erro ao cadastrar categoria!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
    
    public void deletar(Long id) {
        String msg;
        try {
            categoriaRepositorio.delete(id);
            msg = "Categoria deletada com sucesso!";
        } catch (Exception e) {
            msg = "Erro ao deletar categoria!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Categoria> getCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            categorias = categoriaRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar categorias!");
        }
        return categorias;
    }
    
    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepositorio.findById(id);
    }
}
