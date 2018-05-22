package adnyre.maildemo.service;

import adnyre.maildemo.dto.UserDto;
import adnyre.maildemo.dto.UserStatsView;
import adnyre.maildemo.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    User get(long id);
    User save(UserDto dto);
    User update(UserDto dto);
    void delete(long id);
    List<UserStatsView> getAllUserStats();
}