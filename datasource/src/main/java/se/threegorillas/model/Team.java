package se.threegorillas.model;

import se.threegorillas.status.Status;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;



@Entity
public class Team extends AbstractEntity {


	@Column(nullable = false)
	private String teamNameSDFGHJKL;

	@Column
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

	public String getTeamName() {
		return teamName;
	}

	public Collection<User> getUsers() {
		return users;
	}
	
	public void setTeamName(String teamName){
		this.teamName = teamName;
	}

}
