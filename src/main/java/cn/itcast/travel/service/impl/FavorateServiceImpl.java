package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavorateDao;
import cn.itcast.travel.dao.impl.FavorateDaoImpl;
import cn.itcast.travel.service.FavorateService;
import cn.itcast.travel.web.servlet.RouteServlet;

public class FavorateServiceImpl implements FavorateService {
    private FavorateDao favorateDao = new FavorateDaoImpl();
    @Override
    public boolean isFavorate(String ridStr, int uid) {
        int rid = 0;
        if(ridStr != null && !"null".equals(ridStr) && ridStr.length() >0){
             rid = Integer.parseInt(ridStr);
        }
        boolean isFavorate = favorateDao.findByRidAndUid(rid, uid);
        return isFavorate;
    }
}
