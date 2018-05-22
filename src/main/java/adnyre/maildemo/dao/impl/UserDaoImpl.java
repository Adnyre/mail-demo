package adnyre.maildemo.dao.impl;

import adnyre.maildemo.dao.CustomUserDao;
import adnyre.maildemo.dto.UserStatsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static adnyre.maildemo.model.User.GET_USER_STATS;

@Slf4j
@Repository
public class UserDaoImpl implements CustomUserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<UserStatsView> getAllUserStats() {
        TypedQuery<UserStatsView> query = entityManager.createNamedQuery(GET_USER_STATS, UserStatsView.class);
        return query.getResultList();
    }
}