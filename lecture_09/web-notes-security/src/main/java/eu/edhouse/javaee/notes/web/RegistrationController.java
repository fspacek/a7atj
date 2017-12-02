package eu.edhouse.javaee.notes.web;

import eu.edhouse.javaee.notes.business.Owner;
import eu.edhouse.javaee.notes.business.OwnerManager;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {

    @Inject
    private OwnerManager ownerManager;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/registration.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        final Owner owner = new Owner();
        owner.setUsername(request.getParameter("username"));
        owner.setFirstname(request.getParameter("firstname"));
        owner.setLastname(request.getParameter("lastname"));
        owner.setPassword(request.getParameter("password"));
        ownerManager.createUser(owner);

        response.sendRedirect("login");
    }

}
