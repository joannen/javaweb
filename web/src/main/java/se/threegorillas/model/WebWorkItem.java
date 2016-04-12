package se.threegorillas.model;

import se.threegorillas.status.Status;

public final class WebWorkItem {

    private final Long id;
    private final String description;
    private final String assignedUsername;
    private final String issueDescription;
    private final String status;

    public WebWorkItem(Builder builder){

        this.id = builder.id;
        this.description =builder.description;
        this.assignedUsername = builder.assignedUserName;
        this.issueDescription = builder.issue;
        this.status =builder.status;
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

    @Override
    public String toString() {

        return "WebWorkItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", assignedUsername='" + assignedUsername + '\'' +
                ", issueDescription='" + issueDescription + '\'' +
                ", status='" + status + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebWorkItem that = (WebWorkItem) o;

        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    public static class Builder{

        private final Long id;
        private final String description;

        private String assignedUserName = "No assigned user";
        private String issue = "No issue";
        private String status = null;

        public Builder(Long id, String description){
            this.id=id;
            this.description = description;
        }

        public Builder withAssignedUserName(String assignedUserName){
            this.assignedUserName=assignedUserName;
            return this;
        }

        public Builder withIssue(String issue){
            this.issue=issue;
            return this;
        }
        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public WebWorkItem build(){
            return new WebWorkItem(this);
        }

    }

}
