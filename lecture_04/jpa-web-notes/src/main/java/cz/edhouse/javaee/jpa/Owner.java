package cz.edhouse.javaee.jpa;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Frantisek Spacek
 */
@Entity
@Table
public class Owner implements Serializable {

    private long id;
    private String email;
    private String password;
    
    private Set<Note> notes;

    public Owner() {
    }

    
    public Owner(long id) {
        this.id = id;
    }
    
    @Id
    @GeneratedValue(generator = "owner_seq")
    @SequenceGenerator(name = "owner_seq", sequenceName = "OWNER_SEQ", allocationSize = 1, initialValue = 2)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Note> getNotes() {
        if(this.notes == null){
            this.notes = new LinkedHashSet<>();
        }
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.email);
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
        final Owner other = (Owner) obj;
        return Objects.equals(this.email, other.email);
    }

}
