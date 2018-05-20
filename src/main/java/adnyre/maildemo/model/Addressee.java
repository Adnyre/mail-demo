package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Addressee implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "addressee_keyword",
            joinColumns = @JoinColumn(name = "addressee_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords = new HashSet<>();

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords.clear();
        keywords.forEach(this::addKeyword);
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
        keyword.getAddressees().add(this);
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
        keyword.getAddressees().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Addressee addressee = (Addressee) o;

        if (firstName != null ? !firstName.equals(addressee.firstName) : addressee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(addressee.lastName) : addressee.lastName != null) return false;
        if (email != null ? !email.equals(addressee.email) : addressee.email != null) return false;
        return keywords != null ? keywords.equals(addressee.keywords) : addressee.keywords == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Addressee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}