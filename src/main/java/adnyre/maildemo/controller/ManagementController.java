package adnyre.maildemo.controller;

import adnyre.maildemo.business.ManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    @PostMapping("/send-new")
    @ResponseBody
    public ResponseEntity sendToNew(@RequestParam long campaignId) {
        managementService.sendToNewAddressees(campaignId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/send-all")
    @ResponseBody
    public ResponseEntity sendToAll(@RequestParam long campaignId) {
        managementService.sendToAll(campaignId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/resend")
    @ResponseBody
    public ResponseEntity resend(@RequestParam long campaignId) {
        managementService.resend(campaignId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/check-email")
    @ResponseBody
    public ResponseEntity checkEmail(@RequestParam long userId) {
        managementService.checkEmail(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}