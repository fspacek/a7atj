package eu.edhouse.javaee.notes.web;

import eu.edhouse.javaee.notes.business.NoteManager;
import eu.edhouse.javaee.notes.security.UserCallerPrincipal;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "/home")
@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
public class HomeController extends HttpServlet {

    @Inject
    private NoteManager noteManager;

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getParameter("action");
        if ("delete".equals(action)) {
            if (req.getParameter("id") != null) {
                noteManager.delete(Long.valueOf(req.getParameter("id")));
                resp.sendRedirect("home");
                return;
            }
        }

        final UserCallerPrincipal callerPrincipal = (UserCallerPrincipal) securityContext.getCallerPrincipal();
        req.setAttribute("notes", noteManager.getAllForOwner(callerPrincipal.getOwner()));
        getServletContext()
                .getRequestDispatcher("/index.jsp")
                .forward(req, resp);
    }
}
