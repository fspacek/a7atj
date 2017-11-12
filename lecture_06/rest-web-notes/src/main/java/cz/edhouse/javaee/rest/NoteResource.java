package cz.edhouse.javaee.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
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
import javax.ws.rs.core.Response;

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
    public Response getAllNotes() {
        return Response.ok(Note.toJson(noteManager.getAll())).build();
    }

    @POST
    public Response create(@NotNull @Valid Note note) {
        return Response.ok(Note.toJson(noteManager.create(note))).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(Note.toJson(noteManager.getOne(id))).build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        noteManager.delete(id);
    }

}
