package se.threegorillas.model;

import se.threegorillas.status.Status;

import javax.persistence.*;

@Entity
public class WorkItem extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private String status;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Issue issue;

    @ManyToOne
    private User user;

    protected WorkItem(){}

    public WorkItem(String description){
        this.description = description;
        this.status = Status.UNSTARTED;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus(){ return status;}

    public void setStatusUnstarted(){
        this.status = Status.UNSTARTED;
    }

    public void setStatusStarted(){
        this.status = Status.STARTED;
    }

    public void setStatusDone(){
        this.status = Status.DONE;
    }

    public Issue getIssue(){
        return issue;
    }

    public void setIssue(Issue issue){
        this.issue = issue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        if (description != null ? !description.equals(workItem.description) : workItem.description != null)
            return false;
        return status != null ? status.equals(workItem.status) : workItem.status == null;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
