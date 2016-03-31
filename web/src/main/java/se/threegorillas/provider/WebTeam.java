package se.threegorillas.provider;

import java.util.ArrayList;
import java.util.Collection;


public final class WebTeam {

    private final Long id;
    private final String teamName;
    private final String teamStatus;
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

    @Override
    public String toString() {
        return "WebTeam{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamStatus='" + teamStatus + '\'' +
                '}';
    }
}
