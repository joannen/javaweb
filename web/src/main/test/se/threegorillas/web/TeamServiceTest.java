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
import java.util.ArrayList;
import java.util.Collection;

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

    @Test
    public void updatedTeamShouldBeUpdated() {
        WebTeam team = new WebTeam(1L, "meme team");
        WebTarget teamTarget = client.target(teamUrl);

        URI location = teamTarget.request().post(Entity.entity(team, MediaType.APPLICATION_JSON_TYPE)).getLocation();

        WebTarget getTeamById = client.target(location);
        WebTeam retrievedTeam = client.target(location).request().get(WebTeam.class);

        assertEquals(team, retrievedTeam);

        WebTeam updatedTeam = new WebTeam(retrievedTeam.getId(), "new name");

        int status = client.target(location).request()
                                           .put(Entity.entity(updatedTeam, MediaType.APPLICATION_JSON_TYPE))
                                            .getStatus();

        assertEquals(204, status);

    }

    @Test
    public void getAllTeams() {
        WebTarget getAllTeams = client.target(teamUrl);
        System.out.println(getAllTeams.getUri());
        Collection<WebTeam> retrievedWebTeams = getAllTeams.request().get(ArrayList.class);
        assertTrue(retrievedWebTeams.size() > 1);
    }
}
