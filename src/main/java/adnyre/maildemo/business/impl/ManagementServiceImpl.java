package adnyre.maildemo.business.impl;

import adnyre.maildemo.business.ManagementService;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.AddresseeService;
import adnyre.maildemo.service.CampaignService;
import adnyre.maildemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Service
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private AddresseeService addresseeService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    @Async
    public void sendToNewAddressees(long campaignId) {
        Campaign campaign = campaignService.get(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        Set<Addressee> addressees = addresseeService.selectNewAddresseesForCampaign(campaignId);
        log.debug("Got {} new addressees for campaign id: {}", addressees.size(), campaignId);
        sendEmails(campaign, addressees);
    }

    @Override
    @Transactional
    @Async
    public void sendToAll(long campaignId) {
        Campaign campaign = campaignService.get(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        Set<Addressee> addressees = addresseeService.selectAllAddresseesForCampaign(campaignId);
        log.debug("Got {} addressees for campaign id: {}", addressees.size(), campaignId);
        sendEmails(campaign, addressees);
    }

    @Override
    @Transactional
    @Async
    public void resend(long campaignId) {
        Campaign campaign = campaignService.get(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        Set<Addressee> addressees = campaign.getAddressees();
        log.debug("Got {} old addressees for campaign id: {}", addressees.size(), campaign.getId());
        sendEmails(campaign, addressees);
    }

    private void sendEmails(Campaign campaign, Collection<Addressee> addressees) {
        addressees.forEach(addressee -> {
            boolean sent = mailService.sendSingleMessage(campaign.getUser(), addressee, campaign.getMessageTemplate());
            if (sent) {
                campaign.getAddressees().add(addressee);
            }
        });
        log.debug("Finished sending messages for campaign: {}", campaign);
    }

    @Override
    @Transactional
    public void checkEmail(long userId) {
        User user = userService.get(userId);
        log.debug("Checking email for user: {}", user);
        Set<String> emailAddresses = new HashSet<>(mailService.checkEmail(user));
        log.debug("Got emails from {} users", emailAddresses.size());
        List<Addressee> addressees = addresseeService.getByEmails(emailAddresses);
        log.debug("Matched emails to {} addressees in DB", addressees.size());
        user.getAddressees().addAll(addressees);
    }
}