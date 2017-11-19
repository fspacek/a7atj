package eu.edhouse.javaee.jsf;

import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author Frantisek Spacek
 */
@Named
@RequestScoped
public class NoteController {

    @Inject
    private NoteForm noteForm;

    @Inject
    private NoteManager noteManager;

    @Inject
    private Validator validator;

    public String save() {
        final Note toEntity = NoteForm.toEntity(noteForm);
        final Set<ConstraintViolation<Note>> validate = validator.validate(toEntity);
        for (ConstraintViolation error : validate) {
            FacesContext.getCurrentInstance().addMessage("noteForm", new FacesMessage(error.getMessage()));
        }

        if (validate.isEmpty()) {
            noteManager.create(toEntity);
            return "index";
        }
        return "note";
    }
}
