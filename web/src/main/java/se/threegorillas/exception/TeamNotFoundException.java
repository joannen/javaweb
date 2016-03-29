package se.threegorillas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-29.
 */
public class TeamNotFoundException extends WebApplicationException {

    private static final long serialVersionUID = -4158928599202764709L;

    public TeamNotFoundException(Long id){

        super(Response.status(Response.Status.NOT_FOUND).entity(id.toString()).build());
    }

    public TeamNotFoundException(){
        super(Response.Status.NOT_FOUND);
    }
}
