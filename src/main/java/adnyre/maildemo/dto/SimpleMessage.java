package adnyre.maildemo.dto;

import lombok.Data;
import org.springframework.mail.SimpleMailMessage;

import java.io.Serializable;

@Data
public class SimpleMessage implements Serializable {
    private String to;
    private String subject;
    private String text;

    public SimpleMailMessage toSimpleMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}