package eu.edhouse.javaee.notes.business;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Frantisek Spacek
 */
@RunWith(Arquillian.class)
public class NoteManagerIT {

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Note.class, NoteManager.class, TestDataInitializer.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.writeTo(System.out, Formatters.VERBOSE);
        return war;
    }

    @Inject
    private NoteManager instance;

    @Test
    public void testGetAll() throws Exception {
        assertFalse(instance.getAllForOwner(new Owner(1L)).isEmpty());
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
