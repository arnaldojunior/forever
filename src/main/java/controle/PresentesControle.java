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
    
    List<Object[]> listaAlternativa = new ArrayList<>();
    
    private boolean mostrarTabelaAlternativa = false;

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
        return categoriaRepositorio.buscarTodos();
    }

    public List<Object[]> getListaAlternativa() {
        return listaAlternativa;
    }
    
    public boolean isMostrarTabelaAlternativa() {
        return mostrarTabelaAlternativa;
    }

    public void setMostrarTabelaAlternativa(boolean mostrarTabelaAlternativa) {
        this.mostrarTabelaAlternativa = mostrarTabelaAlternativa;
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
        try {
            presenteRepositorio.deletar(id);
            msg = "Presente deletado com sucesso!";
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

    public void buscarPorCategoria(ValueChangeEvent e) {
        this.categoria = e.getNewValue().toString();
        Categoria categoriaBuscada;
        try {
            if (categoria.isEmpty()) {
                this.presentes = presenteRepositorio.buscarTodos();
            } else {
                categoriaBuscada = categoriaRepositorio.buscarPorNome(categoria);
                this.presentes = categoriaBuscada.getPresentes();
                if (presentes.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nenhum presente encontrado!"));
                }
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes por categoria: " + ex);
        }
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
    
    public void valorTotalDosPresentesDaCategoria() {
        Categoria c;
        try {
            c = categoriaRepositorio.buscarPorId(1L);
            listaAlternativa = presenteRepositorio.valorTotalDosPresentesDaCategoria(c);
            
            for (Object[] obj: listaAlternativa) {
                System.out.println("CAT: "+ obj[0] +", VALOR: "+ obj[1]);
            }
            mostrarTabelaAlternativa = true;
            
        } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes mais caros: " + ex);
        }
    }
}