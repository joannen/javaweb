package se.threegorillas.web;

import org.junit.Before;
import org.junit.Test;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.WebWorkItem;
import se.threegorillas.provider.webparser.ArrayListUserProvider;
import se.threegorillas.provider.webparser.UserProvider;
import se.threegorillas.provider.webparser.WorkItemProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private final String userUrl = "http://localhost:8080/web/user";
    private final String teamUrl = "http://localhost:8080/web/team";
    private final String workItemUrl = "http://localhost:8080/web/user";
    private Client client;
    private WebTarget sampleUser;
    private WebTarget postUser;
    private WebTarget userWithId;
    private WebTarget getAllUsers;
    private WebTarget searchForUsers;
    private WebTarget searchForUsersByTeam;


    @Before
    public void setup() {
        client = ClientBuilder.newClient().register(UserProvider.class).register(ArrayListUserProvider.class).register(WorkItemProvider.class);
        sampleUser = client.target(userUrl).path("sample");
        postUser = client.target(userUrl);
        userWithId = client.target(userUrl).path("{id}");
        getAllUsers =client.target(userUrl);
        searchForUsers= client.target(userUrl).path("search");
        searchForUsersByTeam = client.target(teamUrl).path("{id}/user");

    }


    @Test
    public void nullIsNotNull() {
        // atleast one green bar
        assertNull(null);
    }

    @Test
    public void sampleUserIsCorrectlyMapped() {
//        WebUser user = sampleUser.register(UserProvider.class).request(MediaType.APPLICATION_JSON_TYPE)
        WebUser user = sampleUser.request(MediaType.APPLICATION_JSON_TYPE)
                                 .get(WebUser.class);

        assertTrue(user.getUsername().equals("fghj"));
        assertTrue(user.getPassword().equals("sdfghjkl"));
    }

    @Test
    public void shouldBeAbleToCreateUser(){
        WebUser userToSave = new WebUser(1L, "abc",  "joanne", "noriiiiiiiiiiiii", "123", "100000000000001");

        String location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getHeaderString("location");
        WebTarget getUserWithId = client.target(location);
        WebUser retrievedUser = getUserWithId.request().get(WebUser.class);
        assertEquals(userToSave, retrievedUser);

    }

    @Test
    public void updatedUserShouldBeUpdated() {
        WebUser userToSave = new WebUser(1L, "abc", "abc", "abc", "abc", "abc");
        URI location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getUserWithId = client.target(location);
        WebUser retrievedUser = getUserWithId.request().get(WebUser.class);
        assertEquals(userToSave, retrievedUser);

        WebUser updatedUser = new WebUser(retrievedUser.getId(), "cde", "cde", "cde", "cde", "cde");

        int updateStatus = getUserWithId.request().put(Entity.entity(updatedUser, MediaType.APPLICATION_JSON_TYPE)).getStatus();
        assertTrue(updateStatus == 204);

        retrievedUser = userWithId.resolveTemplate("id", updatedUser.getUserNumber()).request().get(WebUser.class);
        System.out.println(retrievedUser);

        assertEquals(retrievedUser, updatedUser);
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNotFound(){
        int status =userWithId.resolveTemplate("id", 10).request().get().getStatus();
        System.out.println(userWithId.resolveTemplate("id", 10).request().get().readEntity(String.class));
        assertEquals(status, 404);
    }

    @Test
    public void shouldBeAbleToSearchForUsers(){

        WebTarget search = searchForUsers.queryParam("query","cde");
        Collection<WebUser> webUsers = search.request().get(ArrayList.class);
        System.out.println(webUsers);
        assertTrue(webUsers.size() >0);
    }

    @Test
    public void addWorkItemToUser(){
        WebWorkItem webWorkItem = new WebWorkItem.Builder(1L, "fghjk").build();
        WebUser userToSave = new WebUser(1L, "abc", "abc", "abc", "abc", "102200010004020009980");
        URI location = postUser.request().post(Entity.entity(userToSave, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        WebTarget getUserWithId = client.target(location);
        System.out.println(location);

        WebTarget userWorkItem= client.target(location).path("workitem");
        URI itemlocation = userWorkItem.request().post(Entity.entity(webWorkItem, MediaType.APPLICATION_JSON_TYPE)).getLocation();
        System.out.println(itemlocation);
    }








}