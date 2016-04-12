package se.threegorillas.provider.mapper;

import se.threegorillas.exception.DeleteException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by TheYellowBelliedMarmot on 2016-04-12.
 */
@Provider
public class DeleteExceptionMapper  implements ExceptionMapper<DeleteException> {

    @Override
    public Response toResponse(DeleteException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

}
