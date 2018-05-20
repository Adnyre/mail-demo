package adnyre.maildemo.service;

import adnyre.maildemo.dto.UserDto;
import adnyre.maildemo.model.User;

public interface UserService {
    User get(long id);
    User save(UserDto dto);
    User update(UserDto dto);
    void delete(long id);
}