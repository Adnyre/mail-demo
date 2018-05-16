package adnyre.maildemo.mail;

public interface MailService {
    void sendSimpleMessage(String to, String subject, String text);
}