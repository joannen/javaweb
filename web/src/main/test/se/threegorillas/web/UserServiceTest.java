package se.threegorillas.web;

import org.junit.Before;
import org.junit.Test;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.webparser.UserProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private final String url = "http://localhost:8080/web/user";
    private Client client;
    private WebTarget sampleUser;
    private WebTarget postUser;
    private WebTarget userWithId;


    @Before
    public void setup() {
        client = ClientBuilder.newClient().register(UserProvider.class);
        sampleUser = client.target(url).path("sample");
        postUser = client.target(url);
        userWithId = client.target(url).path("{id}");

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

        getUserWithId = client.target(location);

        retrievedUser = getUserWithId.request().get(WebUser.class);
        System.out.println(retrievedUser);

        assertEquals(retrievedUser, updatedUser);
    }




}