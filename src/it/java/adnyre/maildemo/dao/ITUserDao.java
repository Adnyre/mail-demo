package adnyre.maildemo.dao;

import adnyre.maildemo.config.TestDbConfig;
import adnyre.maildemo.dto.UserStatsView;
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
public class ITUserDao {

    @Autowired
    private UserDao userDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testGetAllUserStats() throws Exception {
        List<UserStatsView> actualData = userDao.getAllUserStats();
        assertEquals(3, actualData.size());
        Map<Long, UserStatsView> userIdToStatsViewMap = getUserStatsMap(actualData);
        UserStatsView userStats1 = userIdToStatsViewMap.get(1L);
        assertNotNull(userStats1);
        assertTrue(userStats1.getUserName().equals("Bob Brown")
                && userStats1.getEmail().equals("bob@example.com")
                && userStats1.getCampaigns() == 2
                && userStats1.getSentEmails() == 1
                && userStats1.getPotentialEmails() == 2
                && userStats1.getResponses() == 1
                && userStats1.getPotentialResponses() == 1
        );
        UserStatsView userStats2 = userIdToStatsViewMap.get(2L);
        assertNotNull(userStats2);
        assertTrue(userStats2.getUserName().equals("Steve Sanchez")
                && userStats2.getEmail().equals("steve@example.com")
                && userStats2.getCampaigns() == 3
                && userStats2.getSentEmails() == 3
                && userStats2.getPotentialEmails() == 4
                && userStats2.getResponses() == 1
                && userStats2.getPotentialResponses() == 3
        );
        UserStatsView userStats3 = userIdToStatsViewMap.get(3L);
        assertNotNull(userStats3);
        assertTrue(userStats3.getUserName().equals("Chris Campbell")
                && userStats3.getEmail().equals("chris@example.com")
                && userStats3.getCampaigns() == 1
                && userStats3.getSentEmails() == 0
                && userStats3.getPotentialEmails() == 1
                && userStats3.getResponses() == 0
                && userStats3.getPotentialResponses() == 0
        );
    }

    private Map<Long, UserStatsView> getUserStatsMap(List<UserStatsView> userStatsList) {
        return userStatsList.stream()
                .collect(Collectors.toMap(UserStatsView::getUserId, Function.identity()));
    }
}