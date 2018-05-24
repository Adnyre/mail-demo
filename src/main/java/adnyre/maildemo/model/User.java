package adnyre.maildemo.model;

import adnyre.maildemo.dto.UserStatsView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static adnyre.maildemo.model.User.GET_USER_STATS;
import static adnyre.maildemo.model.User.USER_STATS_MAPPING;

@Getter
@Setter
@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = GET_USER_STATS,
                resultSetMapping = USER_STATS_MAPPING,
                query = " SELECT" +
                        "   user.id                                         AS userId," +
                        "   user.name                                       AS userName," +
                        "   user.email                                      AS email," +
                        "   count(DISTINCT campaign.id)                     AS campaigns," +
                        "   count(DISTINCT ca.campaign_id, ca.addressee_id) AS sentEmails," +
                        "   count(DISTINCT campaign.id, ak.addressee_id)    AS potentialEmails," +
                        "   count(DISTINCT ua.addressee_id)                 AS responses," +
                        "   count(DISTINCT ca.addressee_id)                 AS potentialResponses" +
                        " FROM" +
                        "   user" +
                        "   LEFT JOIN" +
                        "   campaign" +
                        "     ON user.id = campaign.user_id" +
                        "   LEFT JOIN" +
                        "   campaign_addressee ca" +
                        "     ON campaign.id = ca.campaign_id" +
                        "   LEFT JOIN" +
                        "   campaign_keyword ck" +
                        "     ON campaign.id = ck.campaign_id" +
                        "   LEFT JOIN" +
                        "   keyword" +
                        "     ON ck.keyword_id = keyword.id" +
                        "   LEFT JOIN" +
                        "   addressee_keyword ak" +
                        "     ON keyword.id = ak.keyword_id" +
                        "   LEFT JOIN" +
                        "   user_addressee ua" +
                        "     ON user.id = ua.user_id" +
                        " GROUP BY" +
                        "   user.id, user.name, user.email")
})
@SqlResultSetMapping(name = USER_STATS_MAPPING, classes = {
        @ConstructorResult(
                targetClass = UserStatsView.class,
                columns = {
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "userName"),
                        @ColumnResult(name = "email"),
                        @ColumnResult(name = "campaigns", type = Long.class),
                        @ColumnResult(name = "sentEmails", type = Long.class),
                        @ColumnResult(name = "potentialEmails", type = Long.class),
                        @ColumnResult(name = "responses", type = Long.class),
                        @ColumnResult(name = "potentialResponses", type = Long.class),
                })
})
public class User implements Serializable {

    public static final String GET_USER_STATS = "getUserStats";
    public static final String USER_STATS_MAPPING = "userStatsMapping";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String pass;

    private String smtpHost;

    private String imapHost;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Campaign> campaigns = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_addressee",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "addressee_id")
    )
    private Set<Addressee> addressees = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        if (smtpHost != null ? !smtpHost.equals(user.smtpHost) : user.smtpHost != null) return false;
        if (imapHost != null ? !imapHost.equals(user.imapHost) : user.imapHost != null) return false;
        if (campaigns != null ? !campaigns.equals(user.campaigns) : user.campaigns != null) return false;
        return addressees != null ? addressees.equals(user.addressees) : user.addressees == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (smtpHost != null ? smtpHost.hashCode() : 0);
        result = 31 * result + (imapHost != null ? imapHost.hashCode() : 0);
        result = 31 * result + (campaigns != null ? campaigns.hashCode() : 0);
        result = 31 * result + (addressees != null ? addressees.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", smtpHost='" + smtpHost + '\'' +
                ", imapHost='" + imapHost + '\'' +
                '}';
    }
}