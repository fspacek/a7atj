package cz.edhouse.javaee.rest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Frantisek Spacek
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name = "Note.getAll", query = "select n from Note n")
    ,
    @NamedQuery(name = "Note.getOne", query = "select n from Note n where n.id = :id")
    ,
    @NamedQuery(name = "Note.getAllWithOwner", query = "select n from Note n where n.owner = :owner")})
public class Note implements Serializable {

    private long id;
    private String title;
    private String text;

    private Owner owner;

    @Id
    @GeneratedValue(generator = "note_seq")
    @SequenceGenerator(name = "note_seq", sequenceName = "NOTE_SEQ", allocationSize = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 1, max = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 1, max = 4000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", title=" + title + ", text=" + text + '}';
    }

    public static Note mapFromResultSet(ResultSet resultSet) throws SQLException {
        final Note note = new Note();
        note.setId(resultSet.getInt("id"));
        note.setTitle(resultSet.getString("title"));
        note.setText(resultSet.getString("text"));
        return note;
    }

    public static String toJson(Note note) {
        return prepareJsonBuilder(note).build().toString();
    }

    public static String toJson(List<Note> notes) {
        final JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
        notes.forEach((note) -> jsonBuilder.add(prepareJsonBuilder(note)));
        return jsonBuilder.build().toString();
    }

    public static Note fromJson(JsonObject jsonObject) {
        final Note note = new Note();
        note.setId(jsonObject.getInt("id", 0));
        note.setTitle(jsonObject.getString("title", ""));
        note.setText(jsonObject.getString("text", ""));
        return note;
    }

    private static JsonObjectBuilder prepareJsonBuilder(Note note) {
        return Json.createObjectBuilder()
                .add("id", note.id)
                .add("title", note.title)
                .add("text", note.text);
    }
}
