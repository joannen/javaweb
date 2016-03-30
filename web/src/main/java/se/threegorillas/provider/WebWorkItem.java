package se.threegorillas.provider;

public final class WebWorkItem {

    private final Long id;
    private final String description;
    private final String assignedUsername;

    public WebWorkItem(Long id, String description, String assignedUsername) {
        this.id = id;
        this.assignedUsername = assignedUsername;
        this.description = description;
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
}
