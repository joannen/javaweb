package se.threegorillas.web;

import se.threegorillas.exception.TeamNotFoundException;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.provider.WebTeam;
import se.threegorillas.provider.WebUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by joanne on 12/04/16.
 */
@Path("/")
public class TeamResource extends AbstractService {

    @GET
    public Response getTeam(@PathParam("id") Long id){

        Team team = service.findTeamById(id);


        WebTeam webTeam = new WebTeam(team.getId(), team.getTeamName(), team.getTeamStatus());

        return Response.ok(webTeam).build();
    }

    @PUT
    public Response updateTeam(@PathParam("id") Long id, WebTeam team){

        Team t = new Team(team.getId(), team.getTeamName(), team.getTeamStatus());

        boolean teamExist = service.teamExists(t);
        Team savedTeam = service.saveTeam(t);

        if(teamExist){
            return Response.noContent().build();
        } else {
            URI location = uriInfo.getAbsolutePathBuilder()
                    .path(TeamService.class, "getTeam")
                    .build(savedTeam.getId());

            return Response.created(location).build();
        }

    }

    @DELETE
    public Response removeTeam(@PathParam("id") Long id){

        Team team = service.findTeamById(id);

        if(team == null){
            throw new TeamNotFoundException(id);
        }

        service.removeTeam(id);

        return Response.noContent().build();

    }

    @POST
    @Path("/user")
    public Response addUserToTeam(@PathParam("id") Long id, WebUser webUser) throws URISyntaxException {
        User u;
        if(service.findUserById(webUser.getId()) != null){
            u=service.findUserById(webUser.getId());
        }else{
            u = new User(webUser.getUsername(), webUser.getFirstName(), webUser.getLastName(), webUser.getPassword(), webUser.getUserNumber());
        }

        Team team = service.findTeamById(id);
        team.addUser(u);
        service.saveUser(u);
        service.saveTeam(team);
//        URI location = uriInfo.getAbsolutePathBuilder().path(u.getUserNumber()).build();
        String baseUri = uriInfo.getBaseUri().toString();
        String location = baseUri +"user"+"/"+u.getUserNumber();


//        URI location = uriInfo.getAbsolutePathBuilder().path(UserService.class, "getUser").build(u.getId());

        return Response.created(new URI(location)).build();
    }

    @GET
    @Path("/user")
    public Collection<WebUser> getAllUsersForTeam(@PathParam("id") Long id){
        Collection<WebUser> webUsers= new ArrayList<>();
        Collection<User> users = service.findTeamById(id).getUsers();

        users.forEach(u -> webUsers.add(new WebUser(u.getId(), u.getFirstName(), u.getLastName(), u.getUserName(), u.getPassword(), u.getUserNumber())));

        return webUsers;
    }
}
