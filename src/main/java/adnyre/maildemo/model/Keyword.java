package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Keyword implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonManagedReference
    @ManyToMany(mappedBy = "keywords")
    private Set<Campaign> campaigns = new HashSet<>();

    @JsonManagedReference
    @ManyToMany(mappedBy = "keywords")
    private Set<Addressee> addressees = new HashSet<>();

    public Keyword() {}

    public Keyword(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword = (Keyword) o;

        if (id != keyword.id) return false;
        return name != null ? name.equals(keyword.name) : keyword.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}