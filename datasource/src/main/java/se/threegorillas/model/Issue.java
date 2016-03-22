package se.threegorillas.model;

import javax.persistence.Embeddable;

@Embeddable
public class Issue {

    private String issueDescription;

    public Issue(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }
}
