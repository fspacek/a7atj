package eu.edhouse.javaee.notes.business;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
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
        final List<Note> all = instance.getAll();

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
