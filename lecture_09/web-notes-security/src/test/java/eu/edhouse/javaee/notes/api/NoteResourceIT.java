package eu.edhouse.javaee.notes.api;

import eu.edhouse.javaee.notes.business.Note;
import eu.edhouse.javaee.notes.business.NoteManager;
import eu.edhouse.javaee.notes.business.TestDataInitializer;
import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

/**
 * @author Frantisek Spacek
 */
@RunWith(Arquillian.class)
public class NoteResourceIT {

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "nr.war")
                .addClasses(Note.class, NoteDto.class, NoteManager.class, NoteResource.class,
                        ApplicationConfig.class, TestDataInitializer.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.writeTo(System.out, Formatters.VERBOSE);
        return war;
    }

    @ArquillianResource
    private URL deploymentUrl;

    @Test
    @RunAsClient
    public void testGetAllNotes() throws Exception {
        final List<Note> notes = when().get(new URL(deploymentUrl, "api/notes")).then()
                .contentType(ContentType.JSON)
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().body().jsonPath().getList("", Note.class);
        assertEquals(10, notes.size());
    }

}
