package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavorateDao;
import cn.itcast.travel.domain.Favorate;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavorateDaoImpl implements FavorateDao {


    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public boolean findByRidAndUid(int rid, int uid) {
        Favorate favorate = null;
        try{
            String sql = "SELECT * from tab_favorite WHERE rid= ? and uid = ?";
            favorate = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorate>(Favorate.class),  rid, uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(favorate == null)
            return false;
        else
            return true;
    }

    @Override
    public int findFavoriteCountByRid(Integer rid) {
        int i = 0;

        try{
            String sql = "SELECT count(*) from tab_favorite where rid = ?";
             i = jdbcTemplate.queryForObject(sql, Integer.class,rid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public void addFavorite(int rid, int uid) {

        try{
            //INSERT INTO  tab_favorite VALUES(2,SYSDATE(),11);
            String sql = "INSERT INTO  tab_favorite VALUES(?,?,?)";
            jdbcTemplate.update(sql, rid, new Date(),  uid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int count(){

            String sql = "SELECT count(*) from tab_favorite ";
            int i = jdbcTemplate.queryForObject(sql, Integer.class);
            return i;
    }


}
