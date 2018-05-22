package adnyre.maildemo.mail;

import adnyre.maildemo.mail.impl.MailServiceImpl;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Test;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ITMailService {

    private MailServiceImpl mailService = new MailServiceImpl(3025, 3143, false);

    @Test
    public void testSendSingleMessage() throws Exception {
        User user = new User();
        user.setEmail("from@localhost.com");
        user.setSmtpHost("127.0.0.1");
        user.setPass("securepass");
        Addressee addressee = new Addressee();
        addressee.setEmail("to@localhost.com");
        String subject = String.valueOf(new Random().nextInt());
        String text = String.valueOf(new Random().nextInt());
        MessageTemplate template = new MessageTemplate();
        template.setSubject(subject);
        template.setTemplate(text);

        GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP_IMAP);
        try {
            greenMail.start();
            mailService.sendSingleMessage(user, addressee, template);
            assertEquals(text, GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
        } finally {
            greenMail.stop();
        }
    }

    @Test
    public void testCheckEmail() throws Exception {
        User user = new User();
        user.setEmail("to@localhost.com");
        user.setImapHost("127.0.0.1");
        user.setPass("securepass");

        GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP_IMAP);
        try {
            greenMail.start();
            String subject = String.valueOf(new Random().nextInt());
            String text = String.valueOf(new Random().nextInt());
            MimeMessage message = createMimeMessage(subject, text, greenMail);
            GreenMailUser greenMailUser = greenMail.setUser("to@localhost.com", "to@localhost.com", "securepass");
            greenMailUser.deliver(message);
            assertEquals(1, greenMail.getReceivedMessages().length);

            List<String> fromList = mailService.checkEmail(user);
            assertEquals(1, fromList.size());
            assertEquals("from@localhost.com", fromList.get(0));
        } finally {
            greenMail.stop();
        }
    }

    private MimeMessage createMimeMessage(String subject, String body, GreenMail greenMail) {
        return GreenMailUtil.createTextEmail("to@localhost.com", "from@localhost.com", subject, body, greenMail.getImap().getServerSetup());
    }
}