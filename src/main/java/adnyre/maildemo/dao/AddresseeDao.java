package adnyre.maildemo.dao;

import adnyre.maildemo.model.Addressee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AddresseeDao extends JpaRepository<Addressee, Long>, CustomAddresseeDao {
    Addressee findByEmail(String email);
    List<Addressee> findByEmailIn(Set<String> email);
}