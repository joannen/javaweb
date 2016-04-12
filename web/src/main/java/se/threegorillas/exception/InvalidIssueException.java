package se.threegorillas.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InvalidIssueException extends WebApplicationException {

    public InvalidIssueException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build());
    }

}
