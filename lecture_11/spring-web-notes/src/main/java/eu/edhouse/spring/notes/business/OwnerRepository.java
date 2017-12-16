package eu.edhouse.spring.notes.business;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface OwnerRepository extends Repository<Owner, Long> {

    Optional<Owner> findOwnerByUsername(String username);

    void save(Owner owner);
}
