package se.threegorillas.web;

import se.threegorillas.model.Issue;
import se.threegorillas.model.WorkItem;
import se.threegorillas.provider.WebWorkItem;
import se.threegorillas.status.Status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/workitem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class WorkItemService extends AbstractService {

    @GET
    @Path("/sample")
    public Response sampleWorkItem() {

        Issue issue = new Issue("things are not done");
        WebWorkItem webWorkItem = new WebWorkItem(1L, "do some things", "froanne", issue.getIssueDescription(), Status.UNSTARTED);

        return Response.ok(webWorkItem).build();
    }

    @GET
    public Response allWorkItems() {
        List<WorkItem> workItems = (List) service.getAllWorkItems();

        /* cast JpaWorkItems to WebWorkItems */
        List<WebWorkItem> webWorkItems = workItems.stream()
                .map(w -> new WebWorkItem(w.getId(), w.getDescription(), w.getAssignedUsername(), w.getStatus()))
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

        WebWorkItem webWorkItem = new WebWorkItem(retrieved.getId(), retrieved.getDescription(),
                retrieved.getAssignedUsername(), retrieved.getStatus());

        return Response.ok(webWorkItem).build();
    }

    @POST
    public Response createWorkItem(WebWorkItem webWorkItem) {
        WorkItem workItem = new WorkItem(webWorkItem.getDescription());
        WorkItem saved = service.saveWorkItem(workItem);

        if (saved == null) {
            throw new WebApplicationException("could not saveWorkItem workitem");
        }

        URI location = uriInfo.getAbsolutePathBuilder().path(WorkItemService.class, "getOneWorkItem").build(saved.getId());

        return Response.created(location).build();
    }

    @PUT
    @Path("{id}")
    public Response updateWorkItem(WebWorkItem webWorkItem) {

        WorkItem workItem = new WorkItem(webWorkItem.getId(), webWorkItem.getDescription());

        boolean exists = service.workItemExists(workItem);

        WorkItem saved = service.saveWorkItem(workItem);

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

    @OPTIONS
    public Response allowedMethods() {
        return Response.noContent()
                .allow("GET", "POST", "PUT", "DELETE")
                .header("Content-Length", 0)
                .build();
    }

    @GET
    @Path("{id}/issue")
    public Response getIssue(@PathParam("id") Long id) {
        WorkItem workItem = service.findWorkItemById(id);

        if (workItem == null) {
            throw new WebApplicationException(404);
        }

        if (workItem.getIssue() == null) {
            throw new WebApplicationException(404);
        }

        return Response.ok(workItem.getIssue().getIssueDescription()).build();
    }

    @POST
    @Path("{id}/issue")
    public Response addIssueToWorkItem(@PathParam("id") Long id, String body) {
        WorkItem workItem = service.findWorkItemById(id);

        if (workItem == null) {
            throw new WebApplicationException(404);
        }

        Issue issue = new Issue(body);

        workItem.setIssue(issue);
        WorkItem workItem1 = service.saveWorkItem(workItem);

        if (workItem1 == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        URI location = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "getOneWorkItem").build();

        return Response.created(location).build();

    }
}
