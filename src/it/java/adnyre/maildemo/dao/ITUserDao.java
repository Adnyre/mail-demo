package adnyre.maildemo.dao;

import adnyre.maildemo.config.TestDbConfig;
import adnyre.maildemo.model.User;
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
    public void testFind() throws Exception {
        User user = userDao.findOne(1L);
        assertTrue(
                user.getId() == 1L
                        && user.getName().equals("Bob Brown")
                        && user.getEmail().equals("bob@example.com"));
    }
}