package eu.edhouse.javaee.notes.security;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Frantisek Spacek
 */
@AutoApplySession
@LoginToContinue
@ApplicationScoped
@DeclareRoles("user")
public class LoginFormAuthenticationMechanism implements HttpAuthenticationMechanism {

    private final IdentityStore identityStore;

    @Inject
    public LoginFormAuthenticationMechanism(IdentityStore identityStore) {
        this.identityStore = identityStore;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res,
                                                HttpMessageContext context) {

        final Credential credential = context.getAuthParameters().getCredential();
        if (credential != null) {
            return context.notifyContainerAboutLogin(identityStore.validate(credential));
        } else {
            return context.doNothing();
        }
    }

    // Workaround for Weld bug; at least in Weld 2.3.2 default methods are not intercepted
    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response,
                             HttpMessageContext httpMessageContext) {
        HttpAuthenticationMechanism.super.cleanSubject(request, response, httpMessageContext);
    }
}
