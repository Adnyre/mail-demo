package adnyre.maildemo.controller;

import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
class TestController {

    // TODO remove
    @Value("${mail.host}")
    private String smtpHost;
    @Value("${imap.host}")
    private String imapHost;
    @Value("${mail.username}")
    private String userEmail;
    @Value("${mail.password}")
    private String pass;
    @Value("${mail.addressee}")
    private String customerEmail;

    private User user;

    private Addressee addressee;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MailService mailService;

    @PostConstruct
    public void init() {
        user = new User();
        user.setEmail(userEmail);
        user.setImapHost(imapHost);
        user.setSmtpHost(smtpHost);
        user.setPass(pass);

        addressee = new Addressee();
        addressee.setEmail(customerEmail);
    }

    @PostMapping("/send-all")
    @ResponseBody
    public void sendToAll(@RequestParam String campaign) {
        campaignService.sendAll(campaign);
    }

    @GetMapping("/receive")
    @ResponseBody
    public void receive() {
        mailService.checkEmail(user);
    }

    @GetMapping("/send")
    @ResponseBody
    public void send() {
        MessageTemplate template = new MessageTemplate();
        template.setSubject("testing");
        template.setTemplate(
                "Dear <==name==>,\n" +
                        "Please, disregard this message.\n" +
                        "Best wishes, maildemo app"
        );
        mailService.sendSingleMessage(user, addressee, template);
    }
}