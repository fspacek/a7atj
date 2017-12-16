package eu.edhouse.spring.notes.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findAllByOwner(Owner owner);
}
