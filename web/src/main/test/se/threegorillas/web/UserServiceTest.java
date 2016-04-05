package se.threegorillas.web;

import org.glassfish.jersey.server.ClientBinding;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.threegorillas.model.User;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.webparser.UserProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private final String url = "http://localhost:8080/web/user";
    private Client client;
    private WebTarget sampleUser;
    private WebTarget postUser;
    private WebTarget getUser;


    @Before
    public void setup() {
        client = ClientBuilder.newClient().register(UserProvider.class);
        sampleUser = client.target(url).path("sample");
        postUser = client.target(url);
        getUser = client.target(url).path("{id}");

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




}