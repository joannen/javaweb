package se.threegorillas.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
public class Issue extends AbstractEntity {

    @Column(nullable = false)
    private String issueDescription;

    protected Issue() {}

    public Issue(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }
}
