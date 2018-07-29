package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<Campaign> campaigns = new ArrayList<>();

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

        return name != null ? name.equals(keyword.name) : keyword.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}