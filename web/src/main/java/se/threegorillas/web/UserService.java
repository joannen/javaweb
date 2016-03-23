package se.threegorillas.web;

import se.threegorillas.provider.WebUser;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by joanne on 22/03/16.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class UserService {

    @Context
    private ServletContext context;

    private DataBaseService service;

    @PostConstruct
    public void setupService() {
        service = (DataBaseService) context.getAttribute("database");
    }

    @GET
    @Path("/sample")
    public Response sampleUser() {

        

        return Response.ok(service.toString()).build();
    }

    @POST
    public Response createUser(WebUser user){

        return Response.ok(user).build();
    }
}
