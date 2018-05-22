package adnyre.maildemo.mail;

import adnyre.maildemo.mail.impl.MailServiceImpl;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ITMailService {

    private MailServiceImpl mailService = new MailServiceImpl(3025);

    @Test
    public void testSendSingleMessage() throws Exception {
        User user = new User();
        user.setEmail("from@localhost.com");
        user.setSmtpHost("");
        user.setPass("");
        Addressee addressee = new Addressee();
        addressee.setEmail("to@localhost.com");
        String subject = String.valueOf(new Random().nextInt());
        String text = String.valueOf(new Random().nextInt());
        MessageTemplate template = new MessageTemplate();
        template.setSubject(subject);
        template.setTemplate(text);

        GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP_IMAP);
        greenMail.start();

        mailService.sendSingleMessage(user, addressee, template);

        assertEquals(text, GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
        greenMail.stop();
    }

}