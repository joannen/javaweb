package se.threegorillas.web;

import se.threegorillas.model.User;
import se.threegorillas.provider.WebUser;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by joanne on 22/03/16.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class UserService {

    @Context
    private ServletContext context;

    @Context
    private UriInfo uriInfo;

    private DataBaseService service;

    @PostConstruct
    public void setupService() {
        service = (DataBaseService) context.getAttribute("database");
    }

    @GET
    @Path("{id}")
    public WebUser getUser(@PathParam("id") Long id){
       User user = service.findById(id);
       WebUser webUser = new WebUser(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getPassword(), user.getUserNumber());
        return webUser;


    }

    @GET
    @Path("/sample")
    public Response sampleUser() {

        WebUser user = new WebUser(1L,"joanne", "nori", "fghj", "sdfghjkl", "1234");

        return Response.ok(user).build();
    }

    @POST
    public Response createUser(WebUser user){
        User u = new User(user.getUsername(),user.getFirstName(), user.getLastName(), user.getPassword(), user.getUserNumber());
        User savedUser = service.saveUser(u);
        URI location = uriInfo.getAbsolutePathBuilder().path(savedUser.getId().toString()).build();
        return Response.created(location).build();
    }
}
