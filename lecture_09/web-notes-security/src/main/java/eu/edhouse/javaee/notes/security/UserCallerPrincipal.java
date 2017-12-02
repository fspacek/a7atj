package eu.edhouse.javaee.notes.security;

import javax.security.enterprise.CallerPrincipal;

public class UserCallerPrincipal extends CallerPrincipal {

    private final Long userId;

    public UserCallerPrincipal(String name, Long userId) {
        super(name);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
