package cz.edhouse.javaee.rest;

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
    
    @Inject
    private NoteManager noteManager;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        final Owner owner = new Owner();
        owner.setEmail("test@test.com");
        owner.setPassword("teeest");
        entityManager.persist(owner);
        
        for(int i = 0; i < 10; i++){
            final Note note = new Note();
            note.setText("test" + i);
            note.setTitle("test title" + i);
            note.setOwner(owner);
            owner.getNotes().add(note);
            noteManager.create(note);
        }
    }
}
