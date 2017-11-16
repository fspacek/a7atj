package cz.edhouse.javaee.jsp;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "/home")
public class IndexController extends HttpServlet {

    @Inject
    private NoteManager noteManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getParameter("action");
        if ("delete".equals(action)) {

            if (req.getParameter("id") != null) {
                noteManager.delete(Long.valueOf(req.getParameter("id")));
                resp.sendRedirect("");
                return;
            }
        }

        req.setAttribute("notes", noteManager.getAll());
        getServletContext()
                .getRequestDispatcher("/index.jsp")
                .forward(req, resp);
    }
}
