package adnyre.maildemo.dao;

import adnyre.maildemo.dto.UserStatsView;

import java.util.List;

public interface CustomUserDao {
    List<UserStatsView> getAllUserStats();
}