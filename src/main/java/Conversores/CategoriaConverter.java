package Conversores;

import controle.CategoriaControle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Categoria;

/**
 *
 * @author Arnaldo Junior
 */
/**
 * Propriedade value permite que o converter seja referenciado
 * diretamente em um UIComponent. 
 */
@FacesConverter(forClass = Categoria.class, value = "categoriaConverter")
public class CategoriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = new Long(value);
        // Busca o controlador no contexto Faces.
        CategoriaControle controle = (CategoriaControle) context.getApplication().
                getELResolver().getValue(context.getELContext(), null, "categoriaControle");
        
        return controle.buscarCategoriaPorId(id);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Categoria) {
            Categoria categoria = (Categoria) object;
            return categoria.getId() == null ? "" : categoria.getId().toString();
        } else {
            throw new IllegalArgumentException("Objeto é do tipo "+ object.getClass().getName());
        }
    }    
}
