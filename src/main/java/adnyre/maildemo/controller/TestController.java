package adnyre.maildemo.controller;

import adnyre.maildemo.dto.SimpleMessage;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
class TestController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MailService mailService;

    @PostMapping("/send-all")
    @ResponseBody
    public void sendToAll(@RequestParam String campaign) {
        campaignService.sendAll(campaign);
    }

    @PostMapping("/send")
    @ResponseBody
    public void send(@RequestBody SimpleMessage message) {
        mailService.sendSimpleMessage(message);
    }

    @GetMapping("/receive")
    @ResponseBody
    public void receive() {
        mailService.fetchEmail(0);
    }
}