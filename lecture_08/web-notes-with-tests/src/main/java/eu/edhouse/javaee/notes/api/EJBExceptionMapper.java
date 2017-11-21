package eu.edhouse.javaee.notes.api;

import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Frantisek Spacek
 */
@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    @Override
    public Response toResponse(EJBException exception) {
        if (exception.getCause() instanceof NoResultException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
         if (exception.getCause() instanceof ValidationException) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
         
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

}
