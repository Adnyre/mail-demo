package adnyre.maildemo.controller;

import adnyre.maildemo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class TestController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/send-all")
    @ResponseBody
    public void sendToAll(@RequestParam String campaign) {
        campaignService.sendAll(campaign);
    }

}