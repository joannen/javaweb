package se.threegorillas.model;

import se.threegorillas.status.Status;

import javax.persistence.*;

@Entity
public class WorkItem extends AbstractEntity{

    @Column(nullable= false)
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @Embedded
    private Issue issue;

    private String workItemStatus;

    public WorkItem(String description) {
        this.description = description;
        this.workItemStatus = Status.ACTIVE;
    }

    public void setStatusUnstarted() {
        this.workItemStatus = Status.INACTIVE;
    }
}
