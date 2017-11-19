package eu.edhouse.javaee.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Frantisek Spacek
 */
@Startup
@Singleton
public class DummyDataInitializator {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        for(int i = 0; i < 10; i++){
            final Note note = new Note();
            note.setText("test" + i);
            note.setTitle("test title" + i);
            entityManager.persist(note);
        }
    }
}
