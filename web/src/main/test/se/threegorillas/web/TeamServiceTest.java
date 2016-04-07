package se.threegorillas.web;

import com.sun.deploy.util.SessionState;
import org.junit.Before;
import org.junit.Test;
import se.threegorillas.provider.WebTeam;
import se.threegorillas.provider.webparser.ArrayListTeamProvider;
import se.threegorillas.provider.webparser.TeamProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by joanne on 07/04/16.
 */
public class TeamServiceTest {

    private final String teamUrl = "http://localhost:8080/web/team";
    private Client client;

    @Before
    public void setup(){
        client = ClientBuilder.newClient().register(TeamProvider.class).register(ArrayListTeamProvider.class);
    }

    @Test
    public void shouldBeAbleToCreateTeam(){

        WebTeam team = new WebTeam(1L, "dreamteam");
        WebTarget teamTarget = client.target(teamUrl);

        String location =teamTarget.request().post(Entity.entity(team, MediaType.APPLICATION_JSON_TYPE)).getHeaderString("location");
        WebTarget getTeamById = client.target(location);
        WebTeam retrievedTeam = getTeamById.request().get(WebTeam.class);

        assertEquals(team, retrievedTeam);

    }
}
