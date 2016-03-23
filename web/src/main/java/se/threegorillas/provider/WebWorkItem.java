package se.threegorillas.provider;

public final class WebWorkItem {

    private final Long id;
    private final String description;

    public WebWorkItem(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
