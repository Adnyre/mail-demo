package adnyre.maildemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static adnyre.maildemo.model.Campaign.GET_CAMPAIGN_STATS;
import static adnyre.maildemo.model.Campaign.GET_CAMPAIGN_STATS_BY_USER_ID;

@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = GET_CAMPAIGN_STATS,
                query = "SELECT new adnyre.maildemo.dto.CampaignStatsView(c.id, c.name," +
                        " u.name, count(distinct ca.id), count(distinct ka.id))" +
                        " FROM Campaign c INNER JOIN c.user u LEFT JOIN c.keywords k" +
                        " LEFT JOIN c.addressees ca LEFT JOIN k.addressees ka" +
                        " GROUP BY c.id, c.name, u.id, u.name"),
        @NamedQuery(name = GET_CAMPAIGN_STATS_BY_USER_ID,
                query = "SELECT new adnyre.maildemo.dto.CampaignStatsView(c.id, c.name," +
                        " u.name, count(distinct ca.id), count(distinct ka.id))" +
                        " FROM Campaign c INNER JOIN c.user u LEFT JOIN c.keywords k" +
                        " LEFT JOIN c.addressees ca LEFT JOIN k.addressees ka" +
                        " WHERE u.id = :userId GROUP BY c.id, c.name, u.name"),
})
public class Campaign implements Serializable {

    public static final String GET_CAMPAIGN_STATS = "getCampaignStats";
    public static final String GET_CAMPAIGN_STATS_BY_USER_ID = "getCampaignStatsByUserId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "campaign_keyword",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "campaign_addressee",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "addressee_id")
    )
    private Set<Addressee> addressees = new HashSet<>();

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