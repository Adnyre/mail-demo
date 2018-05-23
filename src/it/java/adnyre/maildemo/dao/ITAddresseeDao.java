package adnyre.maildemo.dao;

import adnyre.maildemo.config.TestDbConfig;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void testSelectNewAddresseesForCampaign() throws Exception {
        List<Addressee> addressees = addresseeDao.selectNewAddresseesForCampaign(5L);
        assertEquals(1, addressees.size());
        Addressee addressee = addressees.get(0);
        assertTrue(addressee.getId() == 1
                && addressee.getFirstName().equals("Tom")
                && addressee.getLastName().equals("Turner"));
    }

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testSelectAllAddresseesForCampaign() throws Exception {
        List<Addressee> addressees = addresseeDao.selectAllAddresseesForCampaign(5L);
        assertEquals(2, addressees.size());
        Map<Long, Addressee> addresseeMap = getMap(addressees, Addressee::getId);

        Addressee addressee1 = addresseeMap.get(1L);
        assertTrue(addressee1.getFirstName().equals("Tom")
                && addressee1.getLastName().equals("Turner")
                && addressee1.getEmail().equals("tom@example.com"));

        Addressee addressee2 = addresseeMap.get(4L);
        assertTrue(addressee2.getFirstName().equals("Gabriela")
                && addressee2.getLastName().equals("Gutierrez")
                && addressee2.getEmail().equals("gabriela@example.com"));
    }

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void testFindByEmailIn() throws Exception {
        Set<String> addresses = new HashSet<>();
        addresses.addAll(Arrays.asList("tom@example.com", "patricia@example.com", "xabier@example.com"));
        List<Addressee> addressees = addresseeDao.findByEmailIn(addresses);
        assertEquals(2, addressees.size());
        Map<Long, Addressee> addresseeMap = getMap(addressees, Addressee::getId);
        Addressee addressee1 = addresseeMap.get(1L);
        assertTrue(addressee1.getFirstName().equals("Tom")
                && addressee1.getLastName().equals("Turner")
                && addressee1.getEmail().equals("tom@example.com")
        );
        Addressee addressee2 = addresseeMap.get(3L);
        assertTrue(addressee2.getFirstName().equals("Patricia")
                && addressee2.getLastName().equals("Perez")
                && addressee2.getEmail().equals("patricia@example.com")
        );
    }

    private <K, V> Map<K, V> getMap(List<V> list, Function<V, K> mapper) {
        return list.stream().collect(Collectors.toMap(mapper, Function.identity()));
    }
}