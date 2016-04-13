package se.threegorillas.web;

import org.junit.Before;
import org.junit.Test;
import se.threegorillas.model.WebUser;
import se.threegorillas.model.WebWorkItem;
import se.threegorillas.provider.webparser.ArrayListUserProvider;
import se.threegorillas.provider.webparser.UserProvider;
import se.threegorillas.provider.webparser.WorkItemProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class UserServiceTest {

    private final String userUrl = "http://localhost:8080/web/user";
    private Client client;
    private WebTarget sampleUser;
    private WebTarget postUser;
    private WebTarget userWithId;
    private WebTarget searchForUsers;
    private WebTarget searchForWorkItemByUser;

    @Before
    public void setup() {
        client = ClientBuilder.newClient().register(UserProvider.class).register(ArrayListUserProvider.class).register(WorkItemProvider.class);
        sampleUser = client.target(userUrl).path("sample");
        postUser = client.target(userUrl);
        userWithId = client.target(userUrl).path("{usernumber}");
        searchForUsers= client.target(userUrl).path("search");
        searchForWorkItemByUser = client.target(userUrl).path("{usernumber}/workitem");
    }

    @Test
    public void sampleUserIsCorrectlyMapped() {
        WebUser user = sampleUser.request(MediaType.APPLICATION_JSON_TYPE)
                                 .get(WebUser.class);

        assertTrue(user.getUsername().equals("fghj"));
        assertTrue(user.getPassword().equals("sdfghjkl"));
    }

    @Test
    public void shouldBeAbleToCreateUser(){
        WebUser userToSave = new WebUser(1L, "abc",  "joanne", "noriiiiiiiiiiiii", "123", "100000000000001", "0");

        String location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getHeaderString("location");
        WebTarget getUserWithId = client.target(location);
        WebUser retrievedUser = getUserWithId.request().get(WebUser.class);
        assertEquals(userToSave, retrievedUser);
    }

    @Test
    public void updatedUserShouldBeUpdated() {
        WebUser userToSave = new WebUser(1L, "abc", "abc", "abc", "abc", "abc", "0");
        URI location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getUserWithId = client.target(location);
        WebUser retrievedUser = getUserWithId.request().get(WebUser.class);
        assertEquals(userToSave, retrievedUser);

        WebUser updatedUser = new WebUser(retrievedUser.getId(), "cde", "cde", "cde", "cde", "cde", "0");

        int updateStatus = getUserWithId.request().put(Entity.entity(updatedUser, MediaType.APPLICATION_JSON_TYPE)).getStatus();
        assertTrue(updateStatus == 204);

        retrievedUser = userWithId.resolveTemplate("usernumber", updatedUser.getUserNumber()).request().get(WebUser.class);

        assertEquals(retrievedUser, updatedUser);
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNotFound(){
        int status =userWithId.resolveTemplate("usernumber", 10).request().get().getStatus();
        assertEquals(status, 404);
    }

    @Test
    public void shouldBeAbleToSearchForUsers(){
        WebTarget search = searchForUsers.queryParam("query","cde");
        Collection<WebUser> webUsers = search.request().get(ArrayList.class);
        assertTrue(webUsers.size() >0);
    }

    @Test
    public void addWorkItemToUser(){
        WebWorkItem webWorkItem = new WebWorkItem.Builder(1L, "fghjk").build();
        WebUser userToSave = new WebUser(1L, "abc", "abc", "abc", "abc", "102200010004020009980", "0");

        URI location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getUser = client.target(location);
        WebUser retrievedUser = getUser.request().get(WebUser.class);
        WebTarget getWorkItemForUser = searchForWorkItemByUser.resolveTemplate("usernumber", retrievedUser.getUserNumber());

        int addedWorkItemStatus = getWorkItemForUser.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getStatus();
        assertEquals(addedWorkItemStatus, 201);

        Collection<WebWorkItem> webWorkItems = getWorkItemForUser.request().get(ArrayList.class);
        assertTrue(webWorkItems.size() > 0);
    }

}