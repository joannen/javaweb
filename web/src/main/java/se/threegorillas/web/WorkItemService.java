package se.threegorillas.web;

import se.threegorillas.model.WorkItem;
import se.threegorillas.provider.WebWorkItem;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/workitem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class WorkItemService extends AbstractService {

    @GET
    @Path("/sample")
    public Response sampleWorkItem() {
        WebWorkItem webWorkItem = new WebWorkItem(1L, "do some things");

        return Response.ok(webWorkItem).build();
    }

    @GET
    public Response allWorkItems() {
        List<WorkItem> workItems = (List) service.getAllWorkItems();

        List<WebWorkItem> webWorkItems = new ArrayList<>();
        workItems.forEach(w -> webWorkItems.add(new WebWorkItem(w.getId(), w.getDescription())));
        GenericEntity<Collection<WebWorkItem>> entity = new GenericEntity<Collection<WebWorkItem>>(webWorkItems){};

        return Response.ok(entity).build();
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
