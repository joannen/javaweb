package se.threegorillas.web;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
        User user = service.findUserById(id);


        WebUser webUser = new WebUser(user.getId(), user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getPassword(), user.getUserNumber());
        return webUser;


    }

    @GET
    public Collection<WebUser> getAllUsers(){
        Collection<WebUser> webUsers= new ArrayList<>();
        Collection<User> users= service.getAllUsers();
        users.forEach(u -> webUsers.add(new WebUser(u.getId(), u.getFirstName(), u.getLastName(),
                      u.getUserName(), u.getPassword(), u.getUserNumber())));

        return webUsers;
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

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, WebUser webUser){
        User u = new User(webUser.getId(), webUser.getUsername(), webUser.getFirstName(), webUser.getLastName(), webUser.getPassword(), webUser.getUserNumber());
        service.saveUser(u);

        return Response.noContent().build();
    }

    @GET
    @Path("{id}/workitem")
    public Collection<WebWorkItem> getAllWorkItemsForOneUser(@PathParam("id") Long id) {
        User u = service.findUserById(id);

        Collection<WebWorkItem> webWorkItems = u.getWorkItems().stream()

                .map(w -> new WebWorkItem(w.getId(), w.getDescription(), w.getAssignedUsername()))




                .collect(Collectors.toList());

        return webWorkItems;
    }

    @POST
    @Path("{id}/workitem")
    public Response addWorkItemToUser(@PathParam("id") Long id, WebWorkItem webWorkItem) {
        User u = service.findUserById(id);
        WorkItem w = new WorkItem(webWorkItem.getId(), webWorkItem.getDescription());
        u.addWorkItem(w);
        service.saveWorkItem(w);
        service.saveUser(u);

        URI location = uriInfo.getAbsolutePathBuilder().path(WorkItemService.class, "getOneWorkItem").build(w.getId());

        return Response.created(location).build();


    }

}
