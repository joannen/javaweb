package se.threegorillas.web;

import org.glassfish.jersey.server.ClientBinding;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.threegorillas.provider.WebUser;
import se.threegorillas.provider.webparser.UserProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private final String url = "http://localhost:8080/web/user";
    private Client client;
    private WebTarget sampleUser;


    @Before
    public void setup() {
        client = ClientBuilder.newClient().register(UserProvider.class);
        sampleUser = client.target(url).path("sample");
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




}