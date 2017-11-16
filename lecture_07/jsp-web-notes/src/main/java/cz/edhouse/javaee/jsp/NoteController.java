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
@WebServlet(urlPatterns = "/note")
public class NoteController extends HttpServlet {
    
    @Inject
    private NoteManager noteManager;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final NoteForm noteForm = getFromRequest(req);
        noteManager.create(NoteForm.toEntity(noteForm));
        resp.sendRedirect("");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final NoteForm noteForm;
        if (req.getParameter("id") == null || "".equals(req.getParameter("id"))) {
            noteForm = new NoteForm();
        } else {
            noteForm = NoteForm.from(noteManager.getOne(Long.valueOf(req.getParameter("id"))));
        }
        req.setAttribute("noteForm", noteForm);
        getServletContext()
                .getRequestDispatcher("/note.jsp")
                .forward(req, resp);
    }
    
    private NoteForm getFromRequest(HttpServletRequest request) {
        final NoteForm noteForm = new NoteForm();
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            noteForm.setId(Long.valueOf(request.getParameter("id")));
        }
        noteForm.setTitle(request.getParameter("title"));
        noteForm.setText(request.getParameter("text"));
        return noteForm;
    }
}
