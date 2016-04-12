package se.threegorillas.web;

import org.junit.Before;
import org.junit.Test;
import se.threegorillas.model.Issue;
import se.threegorillas.model.WebWorkItem;
import se.threegorillas.provider.webparser.ArrayListWorkItemProvider;
import se.threegorillas.provider.webparser.WorkItemProvider;
import se.threegorillas.status.Status;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by joanne on 08/04/16.
 */

public class WorkItemServiceTest {

    private final String url = "http://localhost:8080/web/workitem";
    private Client client;
    private WebTarget postWorkItem;
    private WebTarget searchForWorkItem;

    @Before
    public void setup(){
        client = ClientBuilder.newClient().register(WorkItemProvider.class).register(ArrayListWorkItemProvider.class);
        postWorkItem = client.target(url);
        searchForWorkItem = client.target(url).path("search");
    }

    @Test
    public void shouldBeAbleToAddWorkItem(){
        WebWorkItem webWorkItem = new WebWorkItem.Builder(1L, "klean kitchen").build();

        URI location = postWorkItem.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getLocation();

        WebTarget getWorkItem = client.target(location);
        WebWorkItem item = getWorkItem.request().get(WebWorkItem.class);

        assertEquals(item, webWorkItem);
    }

    @Test
    public void changeStatusOnWorkItem(){
        WebWorkItem webWorkItem = new WebWorkItem.Builder(1L, "make food").build();

        URI location = postWorkItem.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getWorkitem = client.target(location);
        WebWorkItem retrieved = getWorkitem.request().get(WebWorkItem.class);
        assertTrue(retrieved.getStatus().equals(Status.UNSTARTED));

        WebTarget updateWorkItem = client.target(location).path("status");
        int updated = updateWorkItem.request().put(Entity.entity(Status.STARTED, MediaType.APPLICATION_JSON_TYPE)).getStatus();
        assertEquals(updated, 200);
        WebWorkItem retrievedUpdated = getWorkitem.request().get(WebWorkItem.class);
        assertTrue(retrievedUpdated.getStatus().equals(Status.STARTED));
    }

    @Test
    public void getWorkItemByStatus(){
        WebWorkItem webWorkItem = new WebWorkItem.Builder(1L, "ma1ta katten5").build();

        URI location = postWorkItem.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getWorkitem = client.target(location);
        WebWorkItem retrieved = getWorkitem.request().get(WebWorkItem.class);
        assertTrue(retrieved.getStatus().equals(Status.UNSTARTED));

        WebTarget getByStatus = client.target(url).queryParam("status");
        Collection<WebWorkItem> itemsByStatus = getByStatus.queryParam("status", Status.UNSTARTED).request().get(ArrayList.class);
        assertTrue(itemsByStatus.size() > 0);
    }

    @Test
    public void shouldBeAbleToAddIssueToWorkItem() {
        WebWorkItem webWorkItem = new WebWorkItem.Builder(2L, "Mata hunden").build();

        URI location = postWorkItem.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getLocation();

        WebTarget getWorkItem = client.target(location);
        WebTarget postIssueToWorkItem = client.target(location).path("issue");

        Issue issue = new Issue("Joanne har ingen hund");
        String updatedWorkItem = postIssueToWorkItem.request().post(Entity.entity(issue.getIssueDescription(), MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);
    }

    @Test
    public void shouldBeAbleToSearchForWorkItemByDescription(){
        WebTarget searchTarget = searchForWorkItem.queryParam("query", "hund");
        Collection<WebWorkItem> webWorkItems = searchTarget.request().get(ArrayList.class);
        assertTrue(webWorkItems.size()> 0);
    }

}
