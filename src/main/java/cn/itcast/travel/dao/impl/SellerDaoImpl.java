package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findOne(Integer sid) {
        Seller seller = null;
        try{
            String sql = "select * from tab_seller where sid = ?";
            seller = template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        }catch(Exception e){
            e.printStackTrace();
        }

        return seller;
    }
}
