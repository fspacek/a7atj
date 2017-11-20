package cz.edhouse.javaee.jsp;

/**
 *
 * @author Frantisek Spacek
 */
public class NoteForm {
    
    private Long id;
    private String title;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public static NoteForm from(Note note){
        final NoteForm noteForm = new NoteForm();
        noteForm.setId(note.getId());
        noteForm.setTitle(note.getTitle());
        noteForm.setText(note.getText());
        return noteForm;
    }
    
    public static Note toEntity(NoteForm noteForm){
        final Note note = new Note();
        note.setId(noteForm.getId());
        note.setText(noteForm.getText());
        note.setTitle(noteForm.getTitle());
        return note;
    }

    @Override
    public String toString() {
        return "NoteForm{" + "id=" + id + ", title=" + title + ", text=" + text + '}';
    }
}
