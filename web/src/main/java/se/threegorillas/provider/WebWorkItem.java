package se.threegorillas.provider;

public final class WebWorkItem {

    private final Long id;
    private final String description;
    private final String assignedUsername;
    private final String issueDescription;
    private final String status;

    public WebWorkItem(Long id, String description, String assignedUsername, String issue, String status) {
        this.id = id;
        this.description = description;
        this.assignedUsername = assignedUsername;
        this.issueDescription = issue;
        this.status = status;
    }

    public WebWorkItem(Long id, String description, String assignedUsername, String issue) {
        this.id = id;
        this.description = description;
        this.assignedUsername = assignedUsername;
        this.issueDescription = issue;
        this.status = null;
    }

    public WebWorkItem(Long id, String description, String assignedUsername) {
        this.id = id;
        this.assignedUsername = assignedUsername;
        this.description = description;
        this.issueDescription = null;
        this.status = null;
    }

    public WebWorkItem(Long id, String description){
        this.id = id;
        this.description = description;
        this.assignedUsername = null;
        this.issueDescription = null;
        this.status = null;
    }

    public String getAssignedUsername() {
        return assignedUsername;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getStatus() {
        return status;
    }
}
