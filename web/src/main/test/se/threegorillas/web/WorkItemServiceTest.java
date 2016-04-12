package se.threegorillas.web;

import org.junit.Before;
import org.junit.Test;
import se.threegorillas.provider.WebWorkItem;
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

    @Before
    public void setup(){
        client = ClientBuilder.newClient().register(WorkItemProvider.class).register(ArrayListWorkItemProvider.class);
        postWorkItem = client.target(url);

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

        WebTarget getByStatus = client.target(url).path("status").queryParam("status");
        Collection<WebWorkItem> itemsByStatus = getByStatus.queryParam("status", Status.UNSTARTED).request().get(ArrayList.class);
//        for (WebWorkItem w:itemsByStatus){
//            System.out.println(w);
//        }
//        assertTrue(itemsByStatus.contains(retrieved));
    }

}
