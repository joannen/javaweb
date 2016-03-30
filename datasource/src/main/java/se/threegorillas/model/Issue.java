package se.threegorillas.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class Issue{

    private String issueDescription;

    protected Issue() {}

    public Issue(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        return !(issueDescription != null ? !issueDescription.equals(issue.issueDescription) : issue.issueDescription != null);

    }

    @Override
    public int hashCode() {
        return issueDescription != null ? issueDescription.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueDescription='" + issueDescription + '\'' +
                '}';
    }
}
