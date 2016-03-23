package se.threegorillas.web;

import se.threegorillas.provider.WebWorkItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/workitem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkItemService {

    @GET
    @Path("/sample")
    public Response sampleWorkItem() {
        WebWorkItem webWorkItem = new WebWorkItem(1L, "do some things");
        return Response.ok(webWorkItem).build();
    }
}
