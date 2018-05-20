package adnyre.maildemo.dao;

import adnyre.maildemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}