package cz.edhouse.javaee.jsp;

import java.io.IOException;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 *
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "/note")
public class NoteController extends HttpServlet {

    @Inject
    private NoteManager noteManager;

    @Inject
    private Validator validator;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final NoteForm noteForm = getFromRequest(req);
        final Note entity = NoteForm.toEntity(noteForm);
        final Set<ConstraintViolation<Note>> validationErrors = validator.validate(entity);
        if (!validationErrors.isEmpty()) {
            req.setAttribute("form", noteForm);
            req.setAttribute("errors", validationErrors);
            getServletContext()
                .getRequestDispatcher("/note.jsp")
                .forward(req, resp);
            return;
        }

        if (entity.getId() == null) {
            noteManager.create(entity);
        } else {
            noteManager.update(entity);
        }
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
        System.out.println(noteForm);
        req.setAttribute("form", noteForm);
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
