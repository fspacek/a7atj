package cz.edhouse.javaee.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;

/**
 *
 * @author Frantisek Spacek
 */
public class NoteManager {

    private final EntityManager entityManager;

    public NoteManager(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "entityManager cannot be null");
    }

    public List<Note> getAll() {
        return entityManager.createNamedQuery("Note.getAll", Note.class)
                .getResultList();
    }

    public Optional<Note> getOne(long id) {
        return Optional.ofNullable(entityManager.createNamedQuery("Note.getOne",Note.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    public Note create(Note note) {
         entityManager.persist(note);
         return note;
    }

    public void update(Note note) {
        entityManager.merge(note);
    }

    public void delete(long id) {
        final Note note = new Note();
        note.setId(id);
        entityManager.remove(note);
    }
}
