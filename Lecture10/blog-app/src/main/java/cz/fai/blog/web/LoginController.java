package cz.fai.blog.web;

import cz.fai.blog.bean.SessionBean;
import cz.fai.blog.dto.AuthorDto;
import cz.fai.blog.manager.AuthorManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import static java.lang.String.format;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSContext;

/**
 * Created by Radomir Sohlich on 10/29/16.
 */
@Named
@RequestScoped
public class LoginController {

    private String login;
    private String password;

    @Inject
    private AuthorManager authorManager;

    @Inject
    private SessionBean sessionBean;

    private JMSContext context;
    private Destination notifications;

    @Inject
    public void setContext(JMSContext context) {
        this.context = context;
    }

    @Resource(mappedName = "java:/jms/topics/notifications")
    public void setNotifications(Destination notifications) {
        this.notifications = notifications;
    }

    public String doLogout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public String doLogin() throws IOException {
        AuthorDto authorDto = authorManager.getByEmailAndPassword(login,
                password);
        if (authorDto != null) {
            sessionBean.setUser(authorDto);
            context.createProducer().send(notifications, format("User %s successfully logged", authorDto.getEmail()));
            return "index.xhtml?faces-redirect=true";
        } else {
            return "login.xhtml?faces-redirect=true";
        }
    }

    public void validateLogin(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null || ((String) value).isEmpty()) {
            throw new ValidatorException(new FacesMessage("login cannot be "
                    + "null"));
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
