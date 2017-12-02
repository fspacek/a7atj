package eu.edhouse.javaee.notes.business;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author Frantisek Spacek
 */
@ApplicationScoped
public class TestDataInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) final Object event) {
        System.out.println("Initializing test data");
        for (int i = 0; i < 10; i++) {
            entityManager.persist(new Note("title" + i, "text " + i));
        }
    }
}
