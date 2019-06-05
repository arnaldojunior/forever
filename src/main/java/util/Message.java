package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Arnaldo Junior
 */
public class Message {
    
    public static final void info(String msg) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg));
    }
     
    public static final void warn(String msg) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", msg));
    }
     
    public static final void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", msg));
    }
     
    public static final void fatal(String msg) {
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", msg));
    }
}
