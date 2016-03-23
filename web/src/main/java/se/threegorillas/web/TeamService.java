package se.threegorillas.web;

import se.threegorillas.provider.WebTeam;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by TheYellowBelliedMarmot on 2016-03-23.
 */
@Path("/team")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamService extends AbstractService{

//    @Context
//    private ServletContext context;
//
//    @Context
//    private UriInfo uriInfo;
//
//    private DataBaseService service;
//
//    @PostConstruct
//    public void setUp(){
//        service = (DataBaseService) context.getAttribute("database");
//    }

    @GET
    @Path("/sample")
    public Response sampleTeam(){

        WebTeam sampleTeam = new WebTeam(1L, "MasterTeam", "Active");

        return Response.ok(sampleTeam).build();
    }
}
