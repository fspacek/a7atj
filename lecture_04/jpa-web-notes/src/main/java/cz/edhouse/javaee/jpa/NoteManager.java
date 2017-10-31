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
    
    public List<Note> getAllForOwner(long id){
        return entityManager.createNamedQuery("Note.getAllWithOwner")
                .setParameter("owner", new Owner(id))
                .getResultList();
    }

    public Optional<Note> getOne(long id) {
        return Optional.ofNullable(entityManager.createNamedQuery("Note.getOne", Note.class)
                .setParameter("id", id)
                .getSingleResult());
    }
    
    public Note create(Note note) {
        entityManager.getTransaction().begin();
        entityManager.persist(note);
        entityManager.getTransaction().commit();
        return note;
    }

    public void update(Note note) {
        entityManager.getTransaction().begin();
        entityManager.merge(note);
        entityManager.getTransaction().commit();
    }

    public void delete(long id) {
        final Note note = new Note();
        note.setId(id);
        entityManager.getTransaction().begin();
        entityManager.remove(note);
        entityManager.getTransaction().commit();
    }
}
