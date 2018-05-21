package adnyre.maildemo;

import adnyre.maildemo.config.TestDbConfig;
import adnyre.maildemo.dao.AddresseeDao;
import adnyre.maildemo.model.Addressee;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
@DbUnitConfiguration(databaseConnection = "datasource2")
@Transactional
public class ITAddresseeDao {

    @Autowired
    private AddresseeDao addresseeDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testSelectAddresseesForCampaign() throws Exception {
        List<Addressee> addressees = addresseeDao.selectAddresseesForCampaign(5L);
        assertEquals(1, addressees.size());
        Addressee addressee = addressees.get(0);
        assertTrue(addressee.getId() == 1
                && addressee.getFirstName().equals("Tom")
                && addressee.getLastName().equals("Turner"));
    }
}