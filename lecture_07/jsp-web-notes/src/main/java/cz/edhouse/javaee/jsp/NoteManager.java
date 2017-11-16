package cz.edhouse.javaee.jsp;

import java.util.List;
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

    public List<Note> getAll() {
        return entityManager.createNamedQuery("Note.getAll", Note.class)
                .getResultList();
    }

    public Note getOne(long id) {
        return entityManager.createNamedQuery("Note.getOne", Note.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Note create(Note note) {
        entityManager.persist(note);
        return note;
    }

    public void update(Note note) {
        entityManager.merge(note);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.find(Note.class, id));
    }
}
