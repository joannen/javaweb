package se.threegorillas.web;

import se.threegorillas.exception.EntityNotFoundException;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.provider.WebTeam;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.WebWorkItem;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by joanne on 22/03/16.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class UserService extends AbstractService{



    @GET
    @Path("{userNumber}")
    public WebUser getUser(@PathParam("userNumber") String usernumber) {
        User user = service.findUserByUserNumber(usernumber);

        return toWebUser(user);
    }


    @GET
    public Collection<WebUser> getAllUsers() {
        Collection<WebUser> webUsers = new ArrayList<>();
        Collection<User> users = service.getAllUsers();

        users.forEach(u -> webUsers.add(toWebUser(u)));

        return webUsers;
    }

    @GET
    @Path("/sample")
    public Response sampleUser() {

        WebUser user = new WebUser(1L, "joanne", "nori", "fghj", "sdfghjkl", "1234");

        return Response.ok(user).build();
    }

    @POST
    public Response createUser(WebUser user) {
        User u = new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getUserNumber());
        User savedUser = service.saveUser(u);
        URI location = uriInfo.getAbsolutePathBuilder().path(savedUser.getUserNumber()).build();
        return Response.created(location).build();
    }

    @PUT
    @Path("{userNumber}")
    public Response updateUser(@PathParam("userNumber") String userNumber, WebUser webUser) {
        User userToSave = new User(webUser.getId(), webUser.getUsername(), webUser.getFirstName(), webUser.getLastName(), webUser.getPassword(), webUser.getUserNumber());
        service.saveUser(userToSave);

        return Response.noContent().build();
    }

    @GET
    @Path("{usernumber}/workitem")
    public Collection<WebWorkItem> getAllWorkItemsForOneUser(@PathParam("usernumber") String usernumber) {
        User u = service.findUserByUserNumber(usernumber);

        Collection<WebWorkItem> webWorkItems = u.getWorkItems().stream()
                .map(w -> toWebWorkItem(w))
                .collect(Collectors.toList());
        
        return webWorkItems;
    }

    @POST
    @Path("{id}/workitem")
    public Response addWorkItemToUser(@PathParam("id") String userNumber, WebWorkItem webWorkItem) throws URISyntaxException {
        User u = service.findUserByUserNumber(userNumber);
        WorkItem w;

        try{
            w = service.findWorkItemById(webWorkItem.getId());
        } catch (EntityNotFoundException e){
            w = new WorkItem(webWorkItem.getDescription());
        }

        u.addWorkItem(w);
        service.saveWorkItem(w);
        service.saveUser(u);

        String baseUri = uriInfo.getBaseUri().toString();
        String location = baseUri +"workitem/"+ w.getId();

//        URI location = uriInfo.getAbsolutePathBuilder().path(WorkItemService.class, "getOneWorkItem").build(w.getId());

        return Response.created(new URI(location)).build();


    }

    @GET
    @Path("/search")
    public Collection<WebUser> searchForUser(@QueryParam("query") String query) {
        Collection<User> users = service.searchForUser(query);
        Collection<WebUser> webUsers = new ArrayList<>();

        users.forEach(u -> webUsers.add(new WebUser(u.getId(), u.getFirstName(), u.getLastName(), u.getUserName(), u.getPassword(), u.getUserNumber())));

        return webUsers;
    }




}
