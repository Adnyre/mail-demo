package adnyre.maildemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static adnyre.maildemo.model.User.GET_USER_STATS;

@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = GET_USER_STATS,
                query = "SELECT new adnyre.maildemo.dto.UserStatsView(0L, '', '', 0L, 0L, 0L, 0L)" +
                        " FROM User u WHERE u.id = 1") // TODO implement
})
public class User implements Serializable {

    public static final String GET_USER_STATS = "getUserStats";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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