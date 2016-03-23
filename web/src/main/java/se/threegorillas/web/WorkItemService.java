package se.threegorillas.web;

import se.threegorillas.model.WorkItem;
import se.threegorillas.provider.WebWorkItem;
import se.threegorillas.service.DataBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/workitem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkItemService {

    @Context
    private ServletContext servletContext;

    @Context
    private UriInfo uriInfo;

    private DataBaseService service;

    @PostConstruct
    public void setupDB() {
        service = (DataBaseService) servletContext.getAttribute("database");
    }

    @GET
    @Path("/sample")
    public Response sampleWorkItem() {
        WebWorkItem webWorkItem = new WebWorkItem(1L, "do some things");

        return Response.ok(webWorkItem).build();
    }

    @GET
    @Path("{id}")
    public Response getOneWorkItem(@PathParam("id") Long id) {
        WorkItem retrieved = service.findWorkItemById(id);

        if (retrieved == null) {
            return Response.status(404).build();
        }

        WebWorkItem webWorkItem = new WebWorkItem(retrieved.getId(), retrieved.getDescription());

        return Response.ok(webWorkItem).build();
    }

    @POST
    public Response createWorkItem(WebWorkItem webWorkItem) {
        WorkItem workItem = new WorkItem(webWorkItem.getDescription());
        WorkItem saved = service.save(workItem);

        if (saved == null) {
            throw new WebApplicationException("could not save workitem");
        }

        URI location = uriInfo.getAbsolutePathBuilder().path(WebWorkItem.class, "getOneWorkItem").build(saved.getId());

        return Response.created(location).build();
    }
}
