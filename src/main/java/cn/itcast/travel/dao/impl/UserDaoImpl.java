package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int updateUser(User user) {
        //根据uid更新用户 , username uid code 这些唯一键再用户新建的时候就产生的， email信息也不修改，yin,
        String sql = "update tab_user set password = ?, name = ?, birthday = ?, sex = ?, telephone = ?, email=?, status = ?  " +
                "where uid = ?  ";
        int flag = 0;
        try{
            flag = template.update(sql,user.getPassword(),user.getName(), user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(), user.getUid() );
            return flag;
        }catch (Exception e){
            e.printStackTrace();
            return flag;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
        }

        return user;
    }

    @Override
    public boolean save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        try{
            int result = template.update(sql,user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode()
            );
            if(result == 1)
                return true;
            else
                return false;
        }catch (Exception e){
           e.printStackTrace();
            return false;
        }


    }

    @Override
    public User findUserByCode(String code) {
        //code每一个用户都是唯一的
        User user = null;
        try{
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        User u = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2.执行sql
            u = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (Exception e) {
        }

        return u;
    }

/*    @Override
    public User findUserByUsername(String username) {
        User user = null;
       try{ //由于如果在数据库里面找不到数据，则jdbctemplate会报异常，所以必须用try-catch包住
           //1. sql statement
           String sql = "select * from tab_user where username = ?";
           //2. execute the sql;
           user = (User) template.queryForObject(sql, new BeanPropertyRowMapper(User.class), username);
       }catch (Exception e){

       }
        return user;
    }
    @Override
    public boolean save(User user){
        //1.sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";
        int result = template.update(sql, user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail());
        if(result == 1)
            return true;
        return false;
    }*/
}
