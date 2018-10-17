package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.status.ActiveResult;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public Boolean regist(User user) {  //新建用户
        //1 check the uniqueness of username
        User u = userDao.findUserByUsername(user.getUsername());
        if(u != null){
            return false;
        }
        user.setStatus("N"); //新建用户之处默认是没有激活的
        String code = UuidUtil.getUuid();   // 保证激活码的唯一性
        user.setCode(code);
        Boolean flag = userDao.save(user); // 保存用户到数据库
        if(flag == false)
            return false;  //保存失败直接返回false, 注册失败
        else{
            //发送激活邮件，
            String title = "旅游网－激活邮件";
            String text = "<p>点击连接<a href=\"http://localhost:8080/travel//user/active?code="+ code +"\">激活链接</a>激活旅游网账户 </p>";
            MailUtils.sendMail(user.getEmail(), text, title);
            return true;
        }

    }

    @Override
    public ActiveResult activeUser(String code) {
        //开始激活用户，用唯一的激活码去查询数据库
        User user = null;
        if(code != null) {
            user = userDao.findUserByCode(code);
            if(user == null)
                return ActiveResult.INVALID_CODE;
            if(user.getStatus().equals("N")){
                //第一次激活，执行激活操作
                user.setStatus("Y");
                int flag = userDao.updateUser(user);
                if(flag == 1)
                    return ActiveResult.SUCCESS;
                else
                    return ActiveResult.FAIL;
            }else{
                //已经激活了，不需要更新数据库，返回用户重复激活
                return ActiveResult.REPEAT;
            }
        }else {
            //该激活码为空
            return ActiveResult.INVALID_CODE; //
        }
    }

    @Override
    public User login(User user) {
        return   userDao.findUserByUsernameAndPassword(user);
    }
}
