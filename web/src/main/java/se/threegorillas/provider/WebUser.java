package se.threegorillas.provider;

import java.util.Collection;

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
    public String toString() {
        return "WebUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}