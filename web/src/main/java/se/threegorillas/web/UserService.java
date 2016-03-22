package se.threegorillas.web;

import se.threegorillas.service.DataBaseService;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by joanne on 22/03/16.
 */
@Path("/user")
public final class UserService {

//    @Context
//    private ServletContext context;
//    private DataBaseService service= (DataBaseService) context.getAttribute("database");

    @POST
    public Response createUser(String user){
        return Response.ok(user).build();


    }
}
