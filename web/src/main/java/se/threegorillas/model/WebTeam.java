package se.threegorillas.model;

import se.threegorillas.status.Status;

public final class WebTeam {

    private final Long id;
    private final String teamName;
    private final String teamStatus;

    public WebTeam(Long id, String teamName, String teamStatus){
        this.id=id;
        this.teamName = teamName;
        this.teamStatus = teamStatus;
    }

    public WebTeam(Long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
        this.teamStatus = Status.ACTIVE;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebTeam webTeam = (WebTeam) o;

        if (teamName != null ? !teamName.equals(webTeam.teamName) : webTeam.teamName != null) return false;
        return !(teamStatus != null ? !teamStatus.equals(webTeam.teamStatus) : webTeam.teamStatus != null);
    }

    @Override
    public int hashCode() {
        int result = teamName != null ? teamName.hashCode() : 0;
        result = 31 * result + (teamStatus != null ? teamStatus.hashCode() : 0);
        return result;
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
