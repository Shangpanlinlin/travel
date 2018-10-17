package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int count(int cid, String rname) {
        //String sql = "select count(rid) from tab_route where cid = ? ";
        //cid rname都有可能为空
        String sql = "select count(rid) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> parameters = new ArrayList<>();
        if(cid >0){
            sb.append(" and cid = ? ");
            parameters.add(cid);
        }
        if(rname != null && !"null".equals(rname) && rname.length()>0){
            sb.append(" and rname like ? ");
            parameters.add("%"+rname+"%");
        }

        sql = sb.toString();
        System.out.println(sql);
        System.out.println(parameters.toString());
        return jdbcTemplate.queryForObject(sql, Integer.class, parameters.toArray());
    }

    @Override
    public List<Route> pageQuery(int cid, int startIndex, int pageSize, String rname) {
        //String sql = "SELECT * from tab_route where cid = ? LIMIT ? , ?";
        //拼接参数
         String sql = "SELECT * from tab_route where 1 = 1 ";
         StringBuilder sb = new StringBuilder(sql);
        List<Object> parameters = new ArrayList<>();
         if(cid > 0){
             sb.append("and cid = ? ");
             parameters.add(cid);
         }
         if( rname != null&& !"null".equals(rname) && rname.length() > 0){
             sb.append("and rname like ? ");
             parameters.add("%" + rname + "%");
         }
         sb.append("LIMIT ? , ? ");
         parameters.add(startIndex);
         parameters.add(pageSize);
         sql = sb.toString();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), parameters.toArray());
    }

    /**
     * 根据route id查询route的详情
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        Route route = null;
        try {
            //1.定义sql
            String sql = "select * from tab_route where rid = ?";
            //2.执行sql
            route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return route;
    }

}
