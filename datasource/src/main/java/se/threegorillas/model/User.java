package se.threegorillas.model;

import se.threegorillas.status.Status;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column
	private String userStatus;

	@Column(nullable = false, unique = true)
	private String userNumber;

	@ManyToOne
	private Team team;

	@OneToMany(mappedBy = "user")
	private Collection<WorkItem> workItems;

	protected User() {
	}

	public User(String username, String firstname, String lastname, String password, String userNumber) {
		this.userName = username;
		this.firstName = firstname;
		this.lastName = lastname;
		this.password = password;
		this.workItems = new TreeSet();
		this.userStatus = Status.ACTIVE;
		this.userNumber = userNumber;
	}

	public User(Long id, String username, String firstname, String lastname, String password, String userNumber) {
		this.id = id;
		this.userName = username;
		this.firstName = firstname;
		this.lastName = lastname;
		this.password = password;
		this.workItems = new TreeSet();
		this.userStatus = Status.ACTIVE;
		this.userNumber = userNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public Team getTeam() {
		return team;
	}

	public void addTeam(Team team) {
		this.team = team;
	}

	public Collection<WorkItem> getWorkItems() {
		return new TreeSet<>(workItems);
	}

	public void setStatusInactive() {
		makeAllWorkItemsUnstarted();

		this.userStatus = Status.INACTIVE;
	}

	private void makeAllWorkItemsUnstarted() {
		workItems.forEach(workItem -> workItem.setStatusUnstarted());
	}

	public User deleteWorkItem(WorkItem workItem) {
		this.workItems.remove(workItem);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
