package se.threegorillas.provider;

public final class WebUser {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String userNumber;


    public WebUser(Long id, String firstName, String lastName, String username, String password, String userNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userNumber = userNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebUser webUser = (WebUser) o;

        if (username != null ? !username.equals(webUser.username) : webUser.username != null) return false;
        return !(userNumber != null ? !userNumber.equals(webUser.userNumber) : webUser.userNumber != null);

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userNumber != null ? userNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userNumber='" + userNumber + '\'' +
                '}';
    }
}