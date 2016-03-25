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
import java.util.stream.Collectors;

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

        /* cast JpaWorkItems to WebWorkItems */
        List<WebWorkItem> webWorkItems = workItems.stream()
                .map(w -> new WebWorkItem(w.getId(), w.getDescription()))
                .collect(Collectors.toList());

        return Response.ok(webWorkItems).build();
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
            URI location = uriInfo.getAbsolutePathBuilder()
                    .path(WorkItemService.class, "getOneWorkItem")
                    .build(saved.getId());

            return Response.created(location).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteWorkItem(@PathParam("id") Long id) {

        WorkItem workItem = service.findWorkItemById(id);

        if (workItem == null) {
            throw new WebApplicationException("WorkItem not found", 404);
        }



        return Response.noContent().build();
    }
}
