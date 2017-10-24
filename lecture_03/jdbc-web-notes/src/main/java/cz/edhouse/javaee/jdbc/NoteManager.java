package cz.edhouse.javaee.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Frantisek Spacek
 */
public class NoteManager {

    private static final String GET_NEXT_NOTE_ID_QUERY = "SELECT note_seq.nextval";
    private static final String SELECT_ALL_QUERY = "SELECT id, title, text FROM note";
    private static final String SELECT_ONE_QUERY = "SELECT id, title, text FROM note where id = ?";
    private static final String DELETE_QUERY = "DELETE FROM note where id = ?";
    private static final String INSERT_QUERY = "INSERT INTO note VALUES(note_seq.nextval, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE SET title = ?, text = ? where id = ?";

    private final DataSource dataSource;

    public NoteManager(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource cannot be null");
    }

    public List<Note> getAll() {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(SELECT_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            final List<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                notes.add(Note.mapFromResultSet(resultSet));
            }
            return notes;
        } catch (SQLException ex) {
            throw new DataStorageException("Unable to get all notes", ex);
        }
    }

    public Optional<Note> getOne(long id) {
        return fetchOneById(id);
    }

    public Note create(Note note) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement sequenceStatement = conn.prepareStatement(GET_NEXT_NOTE_ID_QUERY);
                PreparedStatement insertStatement = conn.prepareStatement(INSERT_QUERY)) {
            try (ResultSet sequenceResultSet = sequenceStatement.executeQuery()) {
                if (sequenceResultSet.next()) {
                    final long noteId = sequenceResultSet.getLong(1);
                    note.setId(noteId);
                    insertStatement.setLong(1, note.getId());
                    insertStatement.setString(2, note.getTitle());
                    insertStatement.setString(3, note.getText());
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Unable to create note", ex);
        }
        return note;
    }

    public void update(Note note) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, note.getTitle());
            statement.setString(2, note.getText());
            statement.setLong(3, note.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Unable to update note", ex);
        }
    }

    public void delete(long id) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Unable to get note", ex);
        }
    }

    private Optional<Note> fetchOneById(long id) throws DataStorageException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(SELECT_ONE_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(Note.mapFromResultSet(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Unable to get note", ex);
        }
    }

}
