package eu.edhouse.spring.notes.business;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Frantisek Spacek
 */
@Service
public class OwnerManager implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerManager(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = Objects.requireNonNull(ownerRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    public void createUser(Owner user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ownerRepository.save(user);
    }

    public Optional<Owner> getByName(String name) {
        return ownerRepository.findOwnerByUsername(name);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return ownerRepository.findOwnerByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
