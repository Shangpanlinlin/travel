package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavorateDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavorateDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavorateDao favorateDao = new FavorateDaoImpl();
    /**
     * 根据route id查找route详情
     * @param ridStr
     * @return
     */
    @Override
    public Route findOne(String ridStr) {
        int rid = Integer.parseInt(ridStr);
        Route route = routeDao.findOne(rid);
        if(route != null) {
            List<RouteImg> routeImgList = routeImgDao.findListByRid(route.getRid());
            route.setRouteImgList(routeImgList);
            Seller seller = sellerDao.findOne(route.getSid());
            route.setSeller(seller);
            int count = favorateDao.findFavoriteCountByRid(route.getRid());
            route.setCount(count);

        }
        return route;
    }

    @Override
    public PageBean<Route> queryRoute(int cid, int pageSize, int currentPage, String rname) {
        PageBean<Route> pageBean = new PageBean<>();
        // 1 总共记录数， 2，总共的页数， 3，一页有几条记录  4. 当前在哪一页  5 数据
        int totalCount = routeDao.count(cid, rname);
        int totalPage = (int) ((totalCount + pageSize)/pageSize);

        //分页查询
        int startIndex = (currentPage - 1 )* pageSize;
        List<Route> routeList = routeDao.pageQuery(cid,startIndex, pageSize, rname); //分页查询
        return new PageBean<Route>(totalCount, totalPage,  currentPage,pageSize, routeList);
        }

    @Override
    public void addFavorite(String ridStr, int uid) {
        int rid  = Integer.parseInt(ridStr);

        favorateDao.addFavorite(rid, uid);
    }

}
