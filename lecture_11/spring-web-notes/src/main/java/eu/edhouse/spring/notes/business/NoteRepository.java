package eu.edhouse.spring.notes.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Query("select n from Note n where n.owner = ?#{security.principal}")
    List<Note> findAllByOwner();
}
