package adnyre.maildemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class MessageTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String subject;

    @Type(type="text")
    private String template;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageTemplate template1 = (MessageTemplate) o;

        if (id != template1.id) return false;
        if (subject != null ? !subject.equals(template1.subject) : template1.subject != null) return false;
        return template != null ? template.equals(template1.template) : template1.template == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageTemplate{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", template='" + template + '\'' +
                '}';
    }
}