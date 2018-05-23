package adnyre.maildemo.business.impl;

import adnyre.maildemo.business.ManagementService;
import adnyre.maildemo.mail.MailService;
import adnyre.maildemo.model.*;
import adnyre.maildemo.service.AddresseeService;
import adnyre.maildemo.service.CampaignService;
import adnyre.maildemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ManagementServiceImplTest {

    @Mock
    private CampaignService campaignService;
    @Mock
    private AddresseeService addresseeService;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;
    @InjectMocks
    private ManagementService managementService = new ManagementServiceImpl();

    private Campaign campaign;
    private MessageTemplate template;
    private User user;
    private Addressee addressee1;
    private Addressee addressee2;
    private Addressee addressee3;
    private Addressee addressee4;

    @Before
    public void init() {
        campaign = new Campaign();
        campaign.setId(1);
        campaign.setName("campaign");
        campaign.setMessageTemplate(template);
        user = new User();
        user.setId(1);
        user.setName("userName");
        campaign.setUser(user);
        user.getCampaigns().add(campaign);
        addressee1 = new Addressee();
        addressee1.setId(1);
        addressee2 = new Addressee();
        addressee2.setId(2);
        addressee3 = new Addressee();
        addressee3.setId(3);
        addressee4 = new Addressee();
        addressee4.setId(4);
    }

    @Test
    public void sendToNewAddresseesTest() {
        campaign.getAddressees().addAll(asList(addressee1, addressee2));

        when(campaignService.get(1)).thenReturn(campaign);
        when(addresseeService.selectNewAddresseesForCampaign(1)).thenReturn(asList(addressee3, addressee4));
        when(mailService.sendSingleMessage(user, addressee3, template)).thenReturn(true);
        when(mailService.sendSingleMessage(user, addressee4, template)).thenReturn(false);

        managementService.sendToNewAddressees(1);

        verify(campaignService).get(1);
        verify(addresseeService).selectNewAddresseesForCampaign(1);
        verify(mailService).sendSingleMessage(user, addressee3, template);
        verify(mailService).sendSingleMessage(user, addressee4, template);
        Set<Addressee> sentAddressees = campaign.getAddressees();
        assertEquals(3, sentAddressees.size());
        assertTrue(sentAddressees.containsAll(asList(addressee1, addressee2, addressee3)));
    }

    @Test
    public void sendToAllTest() {
        // TODO implement
    }

    @Test
    public void resendTest() {
        // TODO implement
    }

    @Test
    public void checkEmailTest() {
        // TODO implement
    }
}