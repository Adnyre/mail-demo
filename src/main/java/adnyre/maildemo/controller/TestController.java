package adnyre.maildemo.controller;

import adnyre.maildemo.dao.AddresseeDao;
import adnyre.maildemo.dto.AddresseeDto;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.MessageTemplate;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.AddresseeService;
import adnyre.maildemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
class TestController {

    private static final long USER_ID = 1;
    private static final long CUSTOMER_ID = 1;

    @Autowired
    private MailService mailService;
    @Autowired
    private AddresseeService addresseeService;
    @Autowired
    private UserService userService;

    @Autowired
    private AddresseeDao addresseeDao;

    @GetMapping("/addresses")
    @ResponseBody
    public List<AddresseeDto> getAddressesForCampaign(@RequestParam long campaignId) {
        Set<Addressee> addressees = addresseeDao.selectNewAddresseesForCampaign(campaignId);
        log.debug("Found {} addresses", addressees.size());
        return addressees.stream()
                .map(AddresseeDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/receive")
    @ResponseBody
    public void receive() {
        User user = userService.get(USER_ID);
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
        User user = userService.get(USER_ID);
        Addressee addressee = addresseeService.get(CUSTOMER_ID);
        mailService.sendSingleMessage(user, addressee, template);
    }
}