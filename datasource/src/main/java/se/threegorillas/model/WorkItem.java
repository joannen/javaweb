package se.threegorillas.model;

import se.threegorillas.status.Status;

import javax.persistence.*;

@Entity
public class WorkItem extends AbstractEntity {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Embedded
    private Issue issue;

    @Column
    private String assignedUsername;

    protected WorkItem(){}

    public WorkItem(String description){
        this.description = description;
        this.status = Status.UNSTARTED;
    }
   public WorkItem(Long id, String description){
       this.id = id;
       this.description = description;
       this.status = Status.UNSTARTED;
    }

    public String getAssignedUsername() {
        return assignedUsername;
    }

    public void setAssignedUsername(String assignedUsername) {
        this.assignedUsername = assignedUsername;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        return !(description != null ? !description.equals(workItem.description) : workItem.description != null);

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
