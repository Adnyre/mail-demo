package adnyre.maildemo.business.impl;

import adnyre.maildemo.business.CampaignManagementService;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.model.Campaign;
import adnyre.maildemo.service.AddresseeService;
import adnyre.maildemo.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class CampaignManagementServiceImpl implements CampaignManagementService {

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private AddresseeService addresseeService;
    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public void sendAll(long campaignId) {
        Campaign campaign = campaignService.get(campaignId);
        Assert.notNull(campaign, "Can't find campaign by id: " + campaignId);
        List<Addressee> addressees = addresseeService.selectAddresseesForCampaign(campaignId);
        log.debug("Got {} addresses for campaign id: {}", addressees.size(), campaignId);
        addressees.forEach(addressee -> {
            boolean sent = mailService.sendSingleMessage(campaign.getUser(), addressee, campaign.getMessageTemplate());
            if (sent) {
                campaign.getAddressees().add(addressee);
            }
        });
        log.debug("Finished sending messages for campaign: {}", campaign);
    }
}