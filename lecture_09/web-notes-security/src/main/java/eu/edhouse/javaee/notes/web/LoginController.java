package eu.edhouse.javaee.notes.web;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;

/**
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToLogin(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null) {
            req.logout();
            req.setAttribute("loggedUser", null);
            forwardToLogin(req, resp);
            return;
        }

        final Credential credential = new UsernamePasswordCredential(req.getParameter("username"),
                new Password(req.getParameter("password")));
        final AuthenticationStatus status = securityContext.authenticate(req, resp, AuthenticationParameters
                .withParams().credential(credential));

        if (SUCCESS.equals(status)) {
            resp.sendRedirect("home");
        } else if (SEND_FAILURE.equals(status)) {
            req.setAttribute("message", "Invalid username or password");
            forwardToLogin(req, resp);
        }
    }

    private void forwardToLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher("/login.jsp")
                .forward(req, resp);
    }

}
