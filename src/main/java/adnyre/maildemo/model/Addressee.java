package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static adnyre.maildemo.model.Addressee.SELECT_ALL_ADDRESSEES_FOR_CAMPAIGN;
import static adnyre.maildemo.model.Addressee.SELECT_NEW_ADDRESSEES_FOR_CAMPAIGN;

@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = SELECT_NEW_ADDRESSEES_FOR_CAMPAIGN,
                query = "SELECT DISTINCT a FROM Addressee a INNER JOIN a.keywords ak" +
                        " WHERE ak.name IN (SELECT ck.name FROM Campaign c1 INNER JOIN c1.keywords ck WHERE c1.id = :id)" +
                        " AND a.id NOT IN (SELECT ca.id FROM Campaign c2 INNER JOIN c2.addressees ca WHERE c2.id = :id)"),
        @NamedQuery(name = SELECT_ALL_ADDRESSEES_FOR_CAMPAIGN,
                query = "SELECT DISTINCT a FROM Addressee a INNER JOIN a.keywords ak" +
                        " WHERE ak.name IN (SELECT ck.name FROM Campaign c1 INNER JOIN c1.keywords ck WHERE c1.id = :id)")
})
public class Addressee implements Serializable {

    public static final String SELECT_NEW_ADDRESSEES_FOR_CAMPAIGN = "selectNewAddresseesForCampaign";
    public static final String SELECT_ALL_ADDRESSEES_FOR_CAMPAIGN = "selectAllAddresseesForCampaign";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        Addressee addressee = (Addressee) o;

        if (id != addressee.id) return false;
        if (firstName != null ? !firstName.equals(addressee.firstName) : addressee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(addressee.lastName) : addressee.lastName != null) return false;
        if (email != null ? !email.equals(addressee.email) : addressee.email != null) return false;
        return keywords != null ? keywords.equals(addressee.keywords) : addressee.keywords == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
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