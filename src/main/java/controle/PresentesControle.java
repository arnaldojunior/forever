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

    private String categoria;

    @Inject
    private PresenteRepositorio presenteRepositorio;

    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    List<Presente> presentes = new ArrayList<>();

    @PostConstruct
    public void inicializar() {
        buscarTodosPresentes();
    }

    public Presente getPresente() {
        return presente;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public List<Presente> getPresentes() {
        return presentes;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepositorio.findAll();
    }

    public void cadastrar() {
        String msg;
        try {
            presenteRepositorio.create(presente);
            msg = "Presente " + presente.getDescricao() + " cadastrado com sucesso!";
            presente = new Presente();
        } catch (Exception e) {
            msg = "Erro ao cadastrar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long id) {
        String msg;
        try {
            presenteRepositorio.delete(id);
            msg = "Presente deletado com sucesso!";
            this.presentes = buscarTodosPresentes();
        } catch (Exception e) {
            msg = "Erro ao deletar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Presente> buscarTodosPresentes() {
        try {
            this.presentes = presenteRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar presentes!");
        }
        return presentes;
    }

    public void buscarPorCategoria(ValueChangeEvent e) {
        this.categoria = e.getNewValue().toString();
        Categoria categoriaBuscada;
        try {
            if (categoria.isEmpty()) {
                this.presentes = presenteRepositorio.findAll();
            } else {
                //this.presentes = presenteRepositorio.findByCategoria(categoria);
                 categoriaBuscada = categoriaRepositorio.findByName(categoria);
                 System.out.println("PRESENTES: "+ categoriaBuscada.getPresentes().size());
                 presentes = categoriaBuscada.getPresentes();
                 if (presentes.isEmpty()) {
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nenhum presente encontrado!"));
                 }
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presente por categoria: " + ex);
        }
    }
}
