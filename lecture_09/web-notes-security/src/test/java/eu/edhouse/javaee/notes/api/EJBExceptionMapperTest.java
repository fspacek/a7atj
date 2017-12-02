package eu.edhouse.javaee.notes.api;

import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * @author Frantisek Spacek
 */
public class EJBExceptionMapperTest {

    private EJBExceptionMapper instance;

    @Before
    public void setUp() {
        instance = new EJBExceptionMapper();
    }

    @Test
    public void testToResponse_noResultException() {
        final Response response = instance.toResponse(new EJBException(new NoResultException("test exception")));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponse_validationException() {
        final Response response = instance.toResponse(new EJBException(new ValidationException("test exception")));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testToResponse_otherException() {
        final Response response = instance.toResponse(new EJBException("test exception"));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

}
