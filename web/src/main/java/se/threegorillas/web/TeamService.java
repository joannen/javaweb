package se.threegorillas.web;

import se.threegorillas.exception.TeamNotFoundException;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.model.WorkItem;
import se.threegorillas.provider.WebTeam;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.WebWorkItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-23.
 */
@Path("/team")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class TeamService extends AbstractService {

    @POST
    public Response createTeam(WebTeam webTeam){
        Team team = new Team(webTeam.getTeamName());

        Team saveTeam = service.saveTeam(team);

        URI location = uriInfo.getAbsolutePathBuilder().path(saveTeam.getId().toString()).build(saveTeam.getId());

        return Response.created(location).build();
    }

    @GET
    @Path("/sample")
    public Response sampleTeam(){

        WebTeam sampleTeam = new WebTeam(1L, "MasterTeam", "Active");

        return Response.ok(sampleTeam).build();
    }

    @GET
    @Path("{id}")
    public Response getTeam(@PathParam("id") Long id){

        Team team = service.findTeamById(id);

        if(team == null){
            throw new TeamNotFoundException(id);
        }

        WebTeam webTeam = new WebTeam(team.getId(), team.getTeamName(), team.getTeamStatus());

        return Response.ok(webTeam).build();
    }

    @GET
    public Collection<WebTeam> getAllTeams(){
        List<Team> teams = (List<Team>) service.getAllTeams();

        List<WebTeam> webTeams = teams.stream().map(t -> toWebTeam(t)).collect(Collectors.toList());

        return webTeams;
    }

    @PUT
    @Path("{id}")
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
    @Path("{id}")
    public Response removeTeam(@PathParam("id") Long id){

        Team team = service.findTeamById(id);

        if(team == null){
            throw new TeamNotFoundException(id);
        }

        service.removeTeam(id);

        return Response.noContent().build();

    }

    @POST
    @Path("{id}/user")
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
    @Path("{id}/user")
    public Collection<WebUser> getAllUsersForTeam(@PathParam("id") Long id){
        Collection<WebUser> webUsers= new ArrayList<>();
        Collection<User> users = service.findTeamById(id).getUsers();

        users.forEach(u -> webUsers.add(new WebUser(u.getId(), u.getFirstName(), u.getLastName(), u.getUserName(), u.getPassword(), u.getUserNumber())));

        return webUsers;
    }


    @GET
    @Path("{id}/workitem")
    public Collection<WebWorkItem> getAllWorkItemsForTeam(@PathParam("id") Long id){
        Collection<WebWorkItem> webWorkItems = new ArrayList<>();
        Collection<User> users = service.findUsersByTeamId(id);
            for(User u:users){
            Collection<WorkItem> workItems =u.getWorkItems();
            workItems.forEach(workItem -> webWorkItems.add(toWebWorkItem(workItem)));
        }

//        users.forEach(u -> u.getWorkItems().forEach(w -> webWorkItems.add(toWebWorkItem(w))));
        return webWorkItems;
    }
}




