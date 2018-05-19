package adnyre.maildemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "keywords")
    private Set<Campaign> campaigns;

    @ManyToMany(mappedBy = "keywords")
    private Set<Addressee> addressees;
}