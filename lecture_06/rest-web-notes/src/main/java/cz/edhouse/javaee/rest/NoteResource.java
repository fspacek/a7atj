package cz.edhouse.javaee.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Frantisek Spacek
 */
@Path("/notes")
@Stateless
public class NoteResource {
    
    @Inject
    private NoteManager noteManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllNotes(){
        return noteManager.getAll();
    }
}
