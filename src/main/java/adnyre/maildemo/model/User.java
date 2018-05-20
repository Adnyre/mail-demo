package adnyre.maildemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    @JsonIgnore
    private String pass;

    private String smtpHost;

    private String imapHost;

    @JsonBackReference
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Campaign> campaigns = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        if (smtpHost != null ? !smtpHost.equals(user.smtpHost) : user.smtpHost != null) return false;
        if (imapHost != null ? !imapHost.equals(user.imapHost) : user.imapHost != null) return false;
        return campaigns != null ? campaigns.equals(user.campaigns) : user.campaigns == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (smtpHost != null ? smtpHost.hashCode() : 0);
        result = 31 * result + (imapHost != null ? imapHost.hashCode() : 0);
        result = 31 * result + (campaigns != null ? campaigns.hashCode() : 0);
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