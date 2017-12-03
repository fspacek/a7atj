package eu.edhouse.javaee.notes.security;

import eu.edhouse.javaee.notes.business.Owner;

import javax.security.enterprise.CallerPrincipal;

public class UserCallerPrincipal extends CallerPrincipal {

    private final Owner owner;

    public UserCallerPrincipal(String name, Owner owner) {
        super(name);
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }
}
