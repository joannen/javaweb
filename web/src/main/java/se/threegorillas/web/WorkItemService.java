package se.threegorillas.web;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import com.sun.tools.javac.util.List;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Path("/workitem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkItemService extends AbstractService {

    @GET
    @Path("/sample")
    public Response sampleWorkItem() {
        WebWorkItem webWorkItem = new WebWorkItem(1L, "do some things");

        return Response.ok(webWorkItem).build();
    }

    @GET
    public Response allWorkItems() {
        Collection<WorkItem> workItems = service.getAllWorkItems();

        return Response.ok(workItems).build();
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

        URI location = uriInfo.getAbsolutePathBuilder().path(WorkItemService.class, "getOneWorkItem").build(saved.getId());

        return Response.created(location).build();
    }

    @PUT
    @Path("{id}")
    public Response updateWorkItem(WebWorkItem webWorkItem) {

        WorkItem workItem = new WorkItem(webWorkItem.getId(), webWorkItem.getDescription());

        boolean exists = service.workItemExists(workItem);

        WorkItem saved = service.save(workItem);

        if (exists) {
            return Response.noContent().build();
        } else {
            URI location = uriInfo.getAbsolutePathBuilder().path(WorkItemService.class, "getOneWorkItem").build(saved.getId());
            return Response.created(location).build();
        }

    }
}
