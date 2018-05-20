package adnyre.maildemo.service.impl;

import adnyre.maildemo.dao.UserDao;
import adnyre.maildemo.dto.UserDto;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public User get(long id) {
        return userDao.findOne(id);
    }

    @Override
    @Transactional
    public User save(UserDto dto) {
        Assert.isNull(dto.getId(), "Can save only new entities");
        User user = new User();
        updateEntity(dto, user);
        return user;
    }

    @Override
    @Transactional
    public User update(UserDto dto) {
        Assert.notNull(dto.getId(), "User id should not be null");
        User entity = userDao.findOne(dto.getId());
        Assert.notNull(entity, "Can't find user by id: " + dto.getId());
        updateEntity(dto, entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    private void updateEntity(UserDto dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPass(dto.getPass());
        entity.setSmtpHost(dto.getSmtpHost());
        entity.setImapHost(dto.getImapHost());
    }
}