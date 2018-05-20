package adnyre.maildemo.mail;

import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;

import java.util.List;

public interface MailService {

    List<String> checkEmail(User user);

    boolean sendSingleMessage(User user, Addressee addressee, MessageTemplate template);

    void sendMessages(User user, List<Addressee> addressees, MessageTemplate template);
}