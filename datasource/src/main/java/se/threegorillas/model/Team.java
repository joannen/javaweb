package se.threegorillas.model;

import se.threegorillas.status.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Team extends AbstractEntity {

	@Column(nullable = false)
	private String teamName;

	@Column(nullable = false)
	private String teamStatus;

	@OneToMany(mappedBy = "team")
	private Collection<User> users;

	protected Team() {
	}

	public Team(String teamName) {
		this.teamName = teamName;
		this.teamStatus = Status.ACTIVE;
		users = new HashSet();
	}

	public Team(Long id, String teamName, String teamStatus) {
		this.id = id;
		this.teamName = teamName;
		this.teamStatus = teamStatus;
		users = new HashSet();
	}

	public String getTeamName() {
		return teamName;
	}

	public Collection<User> getUsers() {
		return users;
	}
	
	public void setTeamName(String teamName){
		this.teamName = teamName;
	}

	public String getTeamStatus(){ return teamStatus; }

	public Team addUser(User user){
        user.addTeam(this);
        this.users.add(user);
        return this;
    }

    public void clearUsers(){
        this.users.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return teamName != null ? teamName.equals(team.teamName) : team.teamName == null;
    }

    @Override
    public int hashCode() {
        return teamName != null ? teamName.hashCode() : 0;
    }

    @Override
    public String toString(){
        return this.getTeamName();
    }

}
