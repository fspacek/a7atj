package eu.edhouse.javaee.notes.business;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Frantisek Spacek
 */
@RunWith(Arquillian.class)
public class NoteManagerIT {

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Note.class, NoteManager.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.writeTo(System.out, Formatters.VERBOSE);
        return war;
    }

    @Inject
    private NoteManager instance;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testGetAll() throws Exception {
        assertFalse(instance.getAll().isEmpty());
    }

    @Test
    public void testGetOne() throws Exception {
        assertNotNull(instance.getOne(1L));
    }

    @Test
    public void testCreate() throws Exception {
        final Note note = new Note("test 2", "text 2");
        instance.create(note);
        assertNotNull(note.getId());
    }
}
