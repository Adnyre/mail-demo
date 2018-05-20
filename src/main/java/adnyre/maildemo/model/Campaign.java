package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Campaign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "campaign_keyword",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @JoinColumn(name = "message_template_id")
    private MessageTemplate messageTemplate;

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords.clear();
        keywords.forEach(this::addKeyword);
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
        keyword.getCampaigns().add(this);
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
        keyword.getCampaigns().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Campaign campaign = (Campaign) o;

        if (name != null ? !name.equals(campaign.name) : campaign.name != null) return false;
        if (keywords != null ? !keywords.equals(campaign.keywords) : campaign.keywords != null) return false;
        return messageTemplate != null ? messageTemplate.equals(campaign.messageTemplate) : campaign.messageTemplate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (messageTemplate != null ? messageTemplate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user.getName() +
                ", keywords=" + keywords +
                ", messageTemplate=" + messageTemplate +
                '}';
    }
}