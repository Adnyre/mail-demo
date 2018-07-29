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
    public String toString() {
        return "MessageTemplate{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }
}