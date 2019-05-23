package controle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import modelo.Foto;
import repositorio.FotoRepositorio;

/**
 *
 * 
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class FotosControle implements Serializable {
    
    @Inject
    private Foto foto;
    
    @Inject
    private FotoRepositorio fotoRepositorio;
    
    private Part arquivo;
    
    public void addImagem() {
        String msg;
        try (InputStream input = arquivo.getInputStream()) {
            this.foto.setImagem(inputStreamToByte(input));
            this.fotoRepositorio.create(foto);
            msg = "Foto cadastrada com sucesso.";
            this.foto = new Foto();
        } catch (Exception e) {
            msg = "Erro ao cadastrar foto!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
    
    /**
     * Converte um InputStream em um array de bytes.
     * @param input
     * @return 
     */
    public byte[] inputStreamToByte(InputStream input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException ex) {
            Logger.getLogger(FotosControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toByteArray();
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
    
    public Part getArquivo() {
        return arquivo;
    }

    public void setArquivo(Part arquivo) {
        this.arquivo = arquivo;
    }
}