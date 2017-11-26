package eu.edhouse.javaee.notes.api;

import eu.edhouse.javaee.notes.business.Note;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Frantisek Spacek
 */
public class NoteDtoTest {

    @Test
    public void testFrom() {
        final Note note = new Note();
        note.setId(1L);
        note.setTitle("test");
        note.setText("text");
        final NoteDto dto = NoteDto.from(note);
        assertThat(note.getId()).isEqualTo(dto.getId());
        assertThat(note.getTitle()).isEqualTo(dto.getTitle());
        assertThat(note.getText()).isEqualTo(dto.getText());
    }

    @Test
    public void testToEntity() {
        final NoteDto dto = new NoteDto();
        dto.setId(1L);
        dto.setTitle("title");
        dto.setText("text");

        final Note note = NoteDto.toEntity(dto);
        assertThat(note.getId()).isEqualTo(dto.getId());
        assertThat(note.getTitle()).isEqualTo(dto.getTitle());
        assertThat(note.getText()).isEqualTo(dto.getText());
    }

}
