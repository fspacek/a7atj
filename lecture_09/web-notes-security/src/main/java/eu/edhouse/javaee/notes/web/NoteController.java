package eu.edhouse.javaee.notes.web;

import eu.edhouse.javaee.notes.business.Note;
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
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

/**
 * @author Frantisek Spacek
 */
@WebServlet(urlPatterns = "/note")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class NoteController extends HttpServlet {

    @Inject
    private NoteManager noteManager;

    @Inject
    private Validator validator;

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final NoteForm noteForm = getFromRequest(req);
        final Note entity = NoteForm.toEntity(noteForm);
        final Set<ConstraintViolation<Note>> validationErrors = validator.validate(entity);
        if (!validationErrors.isEmpty()) {
            req.setAttribute("form", noteForm);
            req.setAttribute("errors", validationErrors);
            getServletContext()
                    .getRequestDispatcher("note")
                    .forward(req, resp);
            return;
        }

        final UserCallerPrincipal callerPrincipal = (UserCallerPrincipal) securityContext.getCallerPrincipal();
        entity.setOwner(callerPrincipal.getOwner());
        if (entity.getId() == null) {
            noteManager.create(entity);
        } else {
            noteManager.update(entity);
        }
        resp.sendRedirect("home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final NoteForm noteForm;
        if (req.getParameter("id") == null || "".equals(req.getParameter("id"))) {
            noteForm = new NoteForm();
        } else {
            noteForm = NoteForm.from(noteManager.getOne(Long.valueOf(req.getParameter("id"))));
        }
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
