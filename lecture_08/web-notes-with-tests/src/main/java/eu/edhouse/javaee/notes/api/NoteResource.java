package eu.edhouse.javaee.notes.api;

import eu.edhouse.javaee.notes.business.NoteManager;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Frantisek Spacek
 */
@Stateless
@Path("/notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {


    @Inject
    private NoteManager noteManager;
    
    @GET
    public List<NoteDto> getAllNotes(){
        return noteManager.getAll().stream().map(NoteDto::from)
                .collect(Collectors.toList());
    }
    
     @POST
    public NoteDto create(@NotNull @Valid NoteDto note) {
        return NoteDto.from(noteManager.create(NoteDto.toEntity(note)));
    }

    @GET
    @Path("{id}")
    public NoteDto get(@PathParam("id") long id) {
        return NoteDto.from(noteManager.getOne(id));
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        noteManager.delete(id);
    }
}
