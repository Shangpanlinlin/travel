package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int count(int cid, String rname);

    List<Route> pageQuery(int cid, int startIndex, int pageSize, String rname);

    Route findOne(int rid);
}
