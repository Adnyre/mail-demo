package adnyre.maildemo.dao;

import adnyre.maildemo.config.TestDbConfig;
import adnyre.maildemo.dto.CampaignStatsView;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "datasource2")
@Transactional
public class ITCampaignDao {

    @Autowired
    private CampaignDao campaignDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testCampaignStatsByUserId() throws Exception {
        long userId = 1;
        List<CampaignStatsView> actualData = campaignDao.getCampaignStatsByUserId(userId);
        assertEquals(2, actualData.size());
        Map<Long, CampaignStatsView> campaignIdToStatsViewMap = getCampaignStatsMap(actualData);
        CampaignStatsView campaignStats1 = campaignIdToStatsViewMap.get(1L);
        assertNotNull(campaignStats1);
        assertTrue(campaignStats1.getCampaignName().equals("Birding campaign")
                && campaignStats1.getUserName().equals("Bob Brown")
                && campaignStats1.getSentEmails() == 1
                && campaignStats1.getPotentialAddressees() == 1
        );
        CampaignStatsView campaignStats2 = campaignIdToStatsViewMap.get(2L);
        assertNotNull(campaignStats2);
        assertTrue(campaignStats2.getCampaignName().equals("Anime campaign")
                && campaignStats2.getUserName().equals("Bob Brown")
                && campaignStats2.getSentEmails() == 0
                && campaignStats2.getPotentialAddressees() == 1
        );
    }

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testGetCampaignStats() throws Exception {
        List<CampaignStatsView> actualData = campaignDao.getCampaignStats();
        assertEquals(6, actualData.size());
        Map<Long, CampaignStatsView> campaignIdToStatsViewMap = getCampaignStatsMap(actualData);
        CampaignStatsView campaignStats1 = campaignIdToStatsViewMap.get(1L);
        assertNotNull(campaignStats1);
        assertTrue(campaignStats1.getCampaignName().equals("Birding campaign")
                && campaignStats1.getUserName().equals("Bob Brown")
                && campaignStats1.getSentEmails() == 1
                && campaignStats1.getPotentialAddressees() == 1
        );
        CampaignStatsView campaignStats2 = campaignIdToStatsViewMap.get(2L);
        assertNotNull(campaignStats2);
        assertTrue(campaignStats2.getCampaignName().equals("Anime campaign")
                && campaignStats2.getUserName().equals("Bob Brown")
                && campaignStats2.getSentEmails() == 0
                && campaignStats2.getPotentialAddressees() == 1
        );
        CampaignStatsView campaignStats3 = campaignIdToStatsViewMap.get(3L);
        assertNotNull(campaignStats3);
        assertTrue(campaignStats3.getCampaignName().equals("Knitting campaign")
                && campaignStats3.getUserName().equals("Steve Sanchez")
                && campaignStats3.getSentEmails() == 1
                && campaignStats3.getPotentialAddressees() == 1
        );
        CampaignStatsView campaignStats4 = campaignIdToStatsViewMap.get(4L);
        assertNotNull(campaignStats4);
        assertTrue(campaignStats4.getCampaignName().equals("Politics campaign")
                && campaignStats4.getUserName().equals("Steve Sanchez")
                && campaignStats4.getSentEmails() == 1
                && campaignStats4.getPotentialAddressees() == 1
        );
        CampaignStatsView campaignStats5 = campaignIdToStatsViewMap.get(5L);
        assertNotNull(campaignStats5);
        assertTrue(campaignStats5.getCampaignName().equals("Sport campaign")
                && campaignStats5.getUserName().equals("Steve Sanchez")
                && campaignStats5.getSentEmails() == 1
                && campaignStats5.getPotentialAddressees() == 2
        );

        CampaignStatsView campaignStats6 = campaignIdToStatsViewMap.get(6L);
        assertNotNull(campaignStats6);
        assertTrue(campaignStats6.getCampaignName().equals("Skiing campaign")
                && campaignStats6.getUserName().equals("Chris Campbell")
                && campaignStats6.getSentEmails() == 0
                && campaignStats6.getPotentialAddressees() == 1
        );
    }

    private Map<Long, CampaignStatsView> getCampaignStatsMap(List<CampaignStatsView> actualData) {
        return actualData.stream()
                .collect(Collectors.toMap(CampaignStatsView::getCampaignId, Function.identity()));
    }
}