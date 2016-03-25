package se.threegorillas.web;

import se.threegorillas.model.Team;
import se.threegorillas.provider.WebTeam;
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

/**
 * Created by TheYellowBelliedMarmot on 2016-03-23.
 */
@Path("/team")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamService extends AbstractService {

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
    public WebTeam getTeam(@PathParam("id") Long id){

        Team team = service.findTeamById(id);
        WebTeam webTeam = new WebTeam(team.getId(), team.getTeamName(), team.getTeamStatus());

        return webTeam;
    }

    @GET
    public Collection<WebTeam> getAllTeams(){
        Collection<WebTeam> webTeams = new ArrayList<>();
        Collection<Team> teams = service.getAllTeams();
        teams.forEach(t -> webTeams.add(new WebTeam(t.getId(), t.getTeamName(), t.getTeamStatus())));

        return webTeams;
    }

    @PUT
    @Path("{id}")
    public Response updateTeam(@PathParam("id") Long id, WebTeam team){
        Team t = new Team(team.getId(), team.getTeamName(), team.getTeamStatus());
        service.saveTeam(t);

        return Response.noContent().build();

    }
}
