package adnyre.maildemo.mail;

import adnyre.maildemo.dto.SimpleMessage;

import java.util.List;

public interface MailService {
    List<String> fetchEmail(long userId);
    void sendSimpleMessage(SimpleMessage message);
}