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

import java.util.HashSet;
import java.util.List;
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
        addressee1.setEmail("email1");
        addressee2 = new Addressee();
        addressee2.setId(2);
        addressee2.setEmail("email2");
        addressee3 = new Addressee();
        addressee3.setId(3);
        addressee3.setEmail("email3");
        addressee4 = new Addressee();
        addressee4.setId(4);
        addressee4.setEmail("email4");
        campaign.getAddressees().addAll(asList(addressee1, addressee2));
    }

    @Test
    public void sendToNewAddresseesTest() {
        when(campaignService.get(1)).thenReturn(campaign);
        when(addresseeService.selectNewAddresseesForCampaign(1))
                .thenReturn(new HashSet<>(asList(addressee3, addressee4)));
        when(mailService.sendSingleMessage(user, addressee3, template)).thenReturn(true);
        when(mailService.sendSingleMessage(user, addressee4, template)).thenReturn(false);

        managementService.sendToNewAddressees(1);

        verify(mailService).sendSingleMessage(user, addressee3, template);
        verify(mailService).sendSingleMessage(user, addressee4, template);
        Set<Addressee> sentAddressees = campaign.getAddressees();
        assertEquals(3, sentAddressees.size());
        assertTrue(sentAddressees.containsAll(asList(addressee1, addressee2, addressee3)));
    }

    @Test
    public void sendToAllTest() {
        when(campaignService.get(1)).thenReturn(campaign);
        when(addresseeService.selectAllAddresseesForCampaign(1))
                .thenReturn(new HashSet<>(asList(addressee1, addressee2, addressee3, addressee4)));
        when(mailService.sendSingleMessage(user, addressee1, template)).thenReturn(true);
        when(mailService.sendSingleMessage(user, addressee2, template)).thenReturn(false);
        when(mailService.sendSingleMessage(user, addressee3, template)).thenReturn(true);
        when(mailService.sendSingleMessage(user, addressee4, template)).thenReturn(false);

        managementService.sendToAll(1);

        verify(mailService).sendSingleMessage(user, addressee1, template);
        verify(mailService).sendSingleMessage(user, addressee2, template);
        verify(mailService).sendSingleMessage(user, addressee3, template);
        verify(mailService).sendSingleMessage(user, addressee4, template);
        Set<Addressee> sentAddressees = campaign.getAddressees();
        assertEquals(3, sentAddressees.size());
        assertTrue(sentAddressees.containsAll(asList(addressee1, addressee2, addressee3)));
    }

    @Test
    public void resendTest() {
        when(campaignService.get(1)).thenReturn(campaign);

        managementService.resend(1);

        verify(mailService).sendSingleMessage(user, addressee1, template);
        verify(mailService).sendSingleMessage(user, addressee2, template);
        Set<Addressee> sentAddressees = campaign.getAddressees();
        assertEquals(2, sentAddressees.size());
        assertTrue(sentAddressees.containsAll(asList(addressee1, addressee2)));
    }

    @Test
    public void checkEmailTest() {
        List<String> emails = asList("email1", "email4", "email5", "email8");
        when(userService.get(1)).thenReturn(user);
        when(mailService.checkEmail(user)).thenReturn(emails);
        when(addresseeService.getByEmails(new HashSet<>(emails))).thenReturn(asList(addressee1, addressee4));

        managementService.checkEmail(1);

        assertTrue(user.getAddressees().containsAll(asList(addressee1, addressee4)));
    }
}