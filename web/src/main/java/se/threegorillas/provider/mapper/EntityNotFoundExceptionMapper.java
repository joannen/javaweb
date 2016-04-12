package se.threegorillas.provider.mapper;

import se.threegorillas.exception.EntityNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by joanne on 05/04/16.
 */
@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {

        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();

    }

}
