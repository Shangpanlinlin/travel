package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid查询该路线相关的所有图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findListByRid(Integer rid) {
        List<RouteImg> routeImgList = null;
        try {
            //1.定义sql
            String sql = "select * from tab_route_img where rid = ?";
            //2.执行sql
             routeImgList = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        } catch (Exception e) {
        }

        return routeImgList;
    }
}
