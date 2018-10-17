package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.status.ActiveResult;

public interface UserService {
    public Boolean regist(User user);

    public ActiveResult activeUser(String code);

    User login(User user);
}
