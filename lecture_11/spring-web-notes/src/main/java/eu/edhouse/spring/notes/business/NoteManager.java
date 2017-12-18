package eu.edhouse.spring.notes.business;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Frantisek Spacek
 */
@Service
public class NoteManager {

    private final NoteRepository noteRepository;

    public NoteManager(NoteRepository noteRepository) {
        this.noteRepository = Objects.requireNonNull(noteRepository, "noteRepository must be provided");
    }

    public List<Note> getAllByOwner() {
        return noteRepository.findAllByOwner();
    }

    public Optional<Note> getOne(long id) {
        return noteRepository.findById(id);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void delete(long id) {
        noteRepository.deleteById(id);
    }
}
