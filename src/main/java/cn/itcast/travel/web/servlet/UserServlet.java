package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.status.ActiveResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends BaseServlet {
        public void regist(HttpServletRequest request, HttpServletResponse response){
            //to verify the checkcode
            String check = request.getParameter("check");
            HttpSession session = request.getSession();
            String check_server = (String) session.getAttribute("CHECKCODE_SERVER");
            session.removeAttribute("CHECKCODE_SERVER");
            if(!check.equals(check_server)){
                ResultInfo result = new ResultInfo(false);
                result.setErrorMsg("验证码错误。。。。");
                ObjectMapper objectMapper= new ObjectMapper();
                String serializedResult = null;
                try {
                    serializedResult = objectMapper.writeValueAsString(result);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                response.setContentType("application/json;charset=utf-8");
                try {
                    response.getWriter().write(serializedResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            //1.get information
            Map<String,String[]> paramsMap = request.getParameterMap();

            //2.create user object
            User user = new User();

            //3.populate param into object
            try {
                BeanUtils.populate(user, paramsMap);
                System.out.println(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //4. call the service of registration
            UserService service = new UserServiceImpl();
            Boolean flag = service.regist(user);

            //5. instantiate the result object and return
            ResultInfo result = new ResultInfo(flag);
            if(flag == false) {
                result.setErrorMsg("注册失败。。。。");
            }
            //6.序列化返回结果
            ObjectMapper objectMapper= new ObjectMapper();
            String serializedResult = null;
            try {
                serializedResult = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //7.response to frontend
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().write(serializedResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void logout(HttpServletRequest request, HttpServletResponse response){
            request.getSession().invalidate();
            try {
                response.sendRedirect(request.getContextPath()+"/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void login(HttpServletRequest request, HttpServletResponse response){
            //前端传入用户名和密码，去数据库根据用户名和密码查询user信息
            Map<String, String[]> paramMap = request.getParameterMap();
            User user = new User();
            try {
                //用apache的BeanUtils把前端传进来的参数map
                BeanUtils.populate(user, paramMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserService userService = new UserServiceImpl();
            User u = userService.login(user);
            ResultInfo resultInfo = null;
            if(u == null){
                //用户名或者密码错误
                resultInfo = new ResultInfo(false, "用户名或者密码错误");
            }else{
                if(!"Y".equals(u.getStatus())){
                    //用户未曾激活，登录失败，请先去邮箱激活用户
                    resultInfo = new ResultInfo(false, "用户未曾激活，登录失败，请先去邮箱激活用户");
                }else {
                    resultInfo = new ResultInfo(true, u, "登录成功");
                    request.getSession().setAttribute("user", u);
                }
            }
            //响应数据
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            try {
                objectMapper.writeValue(response.getOutputStream(),resultInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        User user  = (User) session.getAttribute("user");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),user);
    }


    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserService service = new UserServiceImpl();
        ActiveResult flag = service.activeUser(code);
        ResultInfo result = null;

        switch(flag){
            case SUCCESS: //成功激活
                result = new ResultInfo(true, "","");
                break;
            case REPEAT: //重复激活,回复用户已经激活不必再操作了
                result = new ResultInfo(false, null ,"用户已经激活，不需要重复激活");
                break;
            case FAIL: //激活失败，服务器内部错误，请稍后再激活，或者联系运维团队协助激活
                result = new ResultInfo(false, null,"激活失败，服务器内部错误，请稍后再激活，或者联系运维团队协助激活" );
                break;
            case INVALID_CODE:
                result = new ResultInfo(false,null,"激活码无效");
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper ob = new ObjectMapper();
        String jsonResult = ob.writeValueAsString(result);
        response.getWriter().write(jsonResult);
    }

}
