package adnyre.maildemo.dao;

import adnyre.maildemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>, CustomUserDao {
}