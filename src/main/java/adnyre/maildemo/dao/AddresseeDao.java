package adnyre.maildemo.dao;

import adnyre.maildemo.model.Addressee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddresseeDao extends JpaRepository<Addressee, Long>, CustomAddresseeDao {
    Addressee findByEmail(String email);
}