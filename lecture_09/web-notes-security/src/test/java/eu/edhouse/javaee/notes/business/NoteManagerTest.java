package eu.edhouse.javaee.notes.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Frantisek Spacek
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteManagerTest {

    private static final Random RND = new Random(System.currentTimeMillis());

    private NoteManager instance;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Note> query;

    @Before
    public void setUp() {
        instance = new NoteManager();
        instance.setEntityManager(entityManager);
    }

    @Test
    public void testGetAll() throws Exception {
        when(entityManager.createNamedQuery(anyString(), eq(Note.class)))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(asList(note("test 1"), note("test 2")));
        final List<Note> all = instance.getAllForOwner(new Owner(1L));

        assertFalse(all.isEmpty());
        assertEquals(2, all.size());
    }

    private Note note(String title) {
        final Note note = new Note();
        note.setId(RND.nextLong());
        note.setTitle(title);
        note.setText(title + "text");
        return note;
    }

}
