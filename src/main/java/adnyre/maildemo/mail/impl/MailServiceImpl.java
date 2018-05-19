package adnyre.maildemo.mail.impl;

import adnyre.maildemo.Exception.ServiceException;
import adnyre.maildemo.dto.SimpleMessage;
import adnyre.maildemo.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
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

    @Autowired
    private JavaMailSender mailSender;

    public static final Flags CUSTOM_FLAG = new Flags("maildemo");

    // TODO remove
    @Value("${mail.host}")
    private String smtpHost;
    @Value("${imap.host}")
    private String imapHost;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String pass;

    public List<String> fetchEmail(long userId) {

        //TODO get user entity

        try {
            List<String> addresses = new ArrayList<>();

            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");

            // set this session up to use SSL for IMAP connections
            props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // don't fallback to normal IMAP connections on failure.
            props.setProperty("mail.imap.socketFactory.fallback", "false");

            Session emailSession = Session.getDefaultInstance(props);

            Store emailStore = emailSession.getStore("imaps");
            emailStore.connect(imapHost, username, pass);

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
    public void sendSimpleMessage(SimpleMessage message) {
        log.debug("Sending message: {}", message);
        SimpleMailMessage mailMessage = message.toSimpleMailMessage();
        mailSender.send(mailMessage);
        log.debug("Sent message successfully");
    }
}