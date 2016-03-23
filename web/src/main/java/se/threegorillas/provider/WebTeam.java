package se.threegorillas.provider;

import java.util.Collection;

/**
 * Created by joanne on 23/03/16.
 */
public class WebTeam {

    private final Long id;
    private final String teamName;
    private final String teamStatus;
//    private Collection<WebUser> users;

    public WebTeam(Long id, String teamName, String status){

        this.id=id;
        this.teamName = teamName;
        this.teamStatus = status;

    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public Long getId() {
        return id;
    }
}
