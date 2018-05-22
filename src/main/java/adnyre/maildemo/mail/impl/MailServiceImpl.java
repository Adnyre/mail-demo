package adnyre.maildemo.mail.impl;

import adnyre.maildemo.exception.ServiceException;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import adnyre.maildemo.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.ChronoUnit.WEEKS;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private static final Flags CUSTOM_FLAG = new Flags("maildemo");
    private static final int SMTP_PORT = 587;

    @Override
    public List<String> checkEmail(User user) {
        try {
            List<String> addresses = new ArrayList<>();

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(props);

            Store emailStore = emailSession.getStore("imaps");
            emailStore.connect(user.getImapHost(), user.getEmail(), user.getPass());

            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            LocalDate weekBefore = LocalDate.now().minus(1, WEEKS);
            Date weekBeforeOldApi = Date.from(weekBefore.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SentDateTerm dateTerm = new SentDateTerm(SentDateTerm.GT, weekBeforeOldApi);
            FlagTerm flagTerm = new FlagTerm(CUSTOM_FLAG, false);
            SearchTerm searchTerm = new AndTerm(
                    dateTerm, flagTerm
            );
            List<Message> messages = Arrays.asList(emailFolder.search(searchTerm));

            for (Message message : messages) {
                Optional.ofNullable(message.getFrom())
                        .filter(froms -> froms.length > 0)
                        .map(x -> x[0])
                        .filter(from -> from instanceof InternetAddress)
                        .map(x -> ((InternetAddress) x).getAddress())
                        .ifPresent(addresses::add);
                message.setFlags(CUSTOM_FLAG, true);
            }
            emailFolder.close(false);
            emailStore.close();
            log.debug("Received messages from: {}", addresses);
            return addresses;
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean sendSingleMessage(User user, Addressee addressee, MessageTemplate template) {
        Session emailSession = getSession(user);
        try {
            sendMessage(user, template, emailSession, addressee);
            return true;
        } catch (MessagingException exp) {
            log.error("Failed to send message to: {}", addressee.getEmail());
            return false;
        }
    }

    @Override
    public void sendMessages(User user, List<Addressee> addressees, MessageTemplate template) {
        Session emailSession = getSession(user);
        addressees.forEach(a -> {
            try {
                sendMessage(user, template, emailSession, a);
            } catch (MessagingException exp) {
                log.error("Failed to send message to: {}", a.getEmail());
            }
        });
    }

    private Session getSession(User user) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", user.getSmtpHost());
        props.put("mail.smtp.port", SMTP_PORT);

        return Session.getInstance(
                props,
                getAuthenticator(user.getEmail(), user.getPass())
        );
    }

    private static Authenticator getAuthenticator(String username, String pass) {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pass);
            }
        };
    }

    private void sendMessage(User user,
                             MessageTemplate template,
                             Session emailSession,
                             Addressee addressee) throws MessagingException {

        Message mimeMessage = new MimeMessage(emailSession);
        mimeMessage.setFrom(new InternetAddress(user.getEmail()));
        mimeMessage.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(addressee.getEmail())
        );
        mimeMessage.setSubject(template.getSubject());
        String messageText = Util.getTextFromTemplate(template.getTemplate(), addressee);
        mimeMessage.setText(messageText);

        Transport.send(mimeMessage);
        log.debug("Sent email successfully");
    }
}