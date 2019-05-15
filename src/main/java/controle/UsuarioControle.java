package controle;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;
import repositorio.UsuarioRepositorio;

/**
 *
 * 
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class UsuarioControle implements Serializable {
    
    @Inject
    private Usuario usuario;
    
    @Inject
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void addUsuario() {
        String msg;
        try {
            usuarioRepositorio.create(usuario);
            msg = "Usuário " + usuario.getNome() +" cadastrado com sucesso!";
            usuario = new Usuario();
        } catch (Exception e) {
            System.out.println("Erro: "+ e);
            msg = "Erro ao cadastrar o usuário!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
}