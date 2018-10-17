package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    public  Route findOne(String ridStr);
    //因为这是routeservicec查询的是route表里的route数据，所以范型是 Route
    public PageBean<Route> queryRoute(int cid, int pageSize, int currentPage, String rname);

    void addFavorite(String ridStr, int uid);
}
