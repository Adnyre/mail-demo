package adnyre.maildemo.model;

import javax.persistence.*;
import java.util.Set;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public Set<Addressee> getAddressees() {
        return addressees;
    }

    public void setAddressees(Set<Addressee> addressees) {
        this.addressees = addressees;
    }
}