package eu.edhouse.javaee.notes.api;

import eu.edhouse.javaee.notes.business.Note;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Frantisek Spacek
 */
public class NoteDto {

    private long id;
    private String title;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 3, max = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 1, max = 4000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static NoteDto from(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("note cannot be null");
        }
        final NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setText(note.getText());
        return dto;
    }

    public static Note toEntity(NoteDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto cannot be null");
        }
        final Note note = new Note();
        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setText(dto.getText());
        return note;
    }
}
