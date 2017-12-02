package eu.edhouse.javaee.notes.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Frantisek Spacek
 */
@Entity
@NamedQuery(name = "Owner.getByName", query = "select o from Owner o where o.username = :username")
public class Owner implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    private Set<Note> notes;

    public Owner() {
    }

    public Owner(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "owner_seq", sequenceName = "owner_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    public Set<Note> getNotes() {
        if (notes == null) {
            this.notes = new LinkedHashSet<>();
        }
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }


}
