package eu.edhouse.javaee.notes.security;

import eu.edhouse.javaee.notes.business.Owner;
import eu.edhouse.javaee.notes.business.OwnerManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.Set;

/**
 * @author Frantisek Spacek
 */
@ApplicationScoped
public class UserIdentityStore implements IdentityStore {

    private static final Set<String> ROLES = Collections.singleton("user");

    private final OwnerManager ownerManager;
    private final HashGenerator hashGenerator;

    @Inject
    public UserIdentityStore(OwnerManager ownerManager, HashGenerator hashGenerator) {
        this.ownerManager = ownerManager;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            final UsernamePasswordCredential upCredential = (UsernamePasswordCredential) credential;
            final Owner owner = ownerManager.getUserByName(upCredential.getCaller());
            if (owner != null && isPasswordValid(upCredential.getPasswordAsString(), owner.getPassword())) {
                return new CredentialValidationResult(new UserCallerPrincipal(upCredential.getCaller(), owner.getId()),
                        ROLES);
            }
        }
        return CredentialValidationResult.INVALID_RESULT;
    }

    private boolean isPasswordValid(String enteredPassword, String ownerPassword) {
        return hashGenerator.getHashText(enteredPassword).equals(ownerPassword);
    }

}
