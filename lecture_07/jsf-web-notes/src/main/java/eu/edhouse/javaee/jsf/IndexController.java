package eu.edhouse.javaee.jsf;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Frantisek Spacek
 */
@Named
@RequestScoped
public class IndexController {

    @Inject
    private NoteManager noteManager;

    private Long id;

    private List<Note> notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @PostConstruct
    public void init() {
        notes = noteManager.getAll();
    }

    public void deleteNote() {
        noteManager.delete(id);
        notes = noteManager.getAll();
    }

    public String addNote() {
        return "note";
    }
}
