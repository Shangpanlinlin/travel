package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    int updateUser(User user);

    public User findUserByUsername(String username);

    boolean save(User user);

    User findUserByCode(String code);

    User findUserByUsernameAndPassword(User user);
}
