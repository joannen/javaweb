package se.threegorillas.web;

import se.threegorillas.provider.WebUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by joanne on 22/03/16.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class UserService {

//    @Context
//    private ServletContext context;
//    private DataBaseService service= (DataBaseService) context.getAttribute("database");

    @GET
    @Path("/sample")
    public Response sampleUser() {
        return Response.ok(new WebUser("example", "example")).build();
    }

    @POST
    public Response createUser(WebUser user){

        System.out.println(user);

        return Response.ok(user).build();
    }
}
