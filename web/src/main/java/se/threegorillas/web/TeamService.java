package se.threegorillas.web;

import se.threegorillas.exception.TeamNotFoundException;
import se.threegorillas.model.Team;
import se.threegorillas.model.User;
import se.threegorillas.provider.WebTeam;
import se.threegorillas.provider.WebUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
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

        URI location = uriInfo.getAbsolutePathBuilder().path(saveTeam.getId().toString()).build();

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
    public Response getAllTeams(){

        List<Team> teams = (List<Team>) service.getAllTeams();

        if(teams.isEmpty()){

            throw new TeamNotFoundException();
        }

        List<WebTeam> webTeams = teams.stream().map(t -> new WebTeam(t.getId(), t.getTeamName(), t.getTeamStatus())).collect(Collectors.toList());

        return Response.ok(webTeams).build();

//        Collection<WebTeam> webTeams = new ArrayList<>();
//        Collection<Team> teams = service.getAllTeams();
//        teams.forEach(t -> webTeams.add(new WebTeam(t.getId(), t.getTeamName(), t.getTeamStatus())));
//
//        return webTeams;
    }

    @PUT
    @Path("{id}")
    public Response updateTeam(@PathParam("id") Long id, WebTeam team){
        
        Team t = new Team(team.getId(), team.getTeamName(), team.getTeamStatus());
        service.saveTeam(t);

        return Response.noContent().build();

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
    public Response addUserToTeam(@PathParam("id") Long id, WebUser webUser){
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

        URI location = uriInfo.getAbsolutePathBuilder().path(UserService.class, "getUser").build(u.getId());

        return Response.created(location).build();
    }

    @GET
    @Path("{id}/user")
    public Collection<WebUser> getAllUsersForTeam(@PathParam("id") Long id){
        Collection<WebUser> webUsers= new ArrayList<>();
        Collection<User> users = service.findTeamById(id).getUsers();
        users.forEach(u -> webUsers.add(new WebUser(u.getId(), u.getFirstName(), u.getLastName(), u.getUserName(), u.getPassword(), u.getUserNumber())));

        return webUsers;
    }


}
