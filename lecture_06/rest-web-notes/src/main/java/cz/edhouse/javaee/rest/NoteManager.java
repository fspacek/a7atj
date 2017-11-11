package cz.edhouse.javaee.rest;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Frantisek Spacek
 */
@Stateless
public class NoteManager {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @LogAround
    public List<Note> getAll() {
        return entityManager.createNamedQuery("Note.getAll", Note.class)
                .getResultList();
    }

    public List<Note> getAllForOwner(long id) {
        return entityManager.createNamedQuery("Note.getAllWithOwner", Note.class)
                .setParameter("owner", new Owner(id))
                .getResultList();
    }

    public Optional<Note> getOne(long id) {
        return Optional.ofNullable(entityManager.createNamedQuery("Note.getOne", Note.class)
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
