package eu.edhouse.spring.notes.web;

import eu.edhouse.spring.notes.business.Owner;

public class RegistrationForm {

    private String username;
    private String firstname;
    private String lastname;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Owner createOwner() {
        final Owner owner = new Owner();
        owner.setUsername(username);
        owner.setFirstname(firstname);
        owner.setLastname(lastname);
        owner.setPassword(password);
        return owner;
    }
}
