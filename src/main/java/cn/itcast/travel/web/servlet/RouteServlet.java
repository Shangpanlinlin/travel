package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavorateService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavorateServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(urlPatterns = "/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService= new RouteServiceImpl();
    private FavorateService favorateService =new FavorateServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");
        String cid = request.getParameter("cid");
        String pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("rname");

        int currentPageInt = 1;  //如果前端没有数据传输过来默认第一页
        if(currentPage != null && currentPage.length()>0){
            currentPageInt = Integer.parseInt(currentPage);
        }
        int cidInt = 0;
        if(cid != null && cid.length() >0 && !"null".equals(cid)){
            cidInt = Integer.parseInt(cid);
        }
        int pageSizeInt = 5;
        if(pageSize != null && pageSize.length() > 0 ){
            pageSizeInt = Integer.parseInt(pageSize);
        }
        PageBean<Route> pageBean = routeService.queryRoute(cidInt,pageSizeInt, currentPageInt, rname);
        this.writeValue(pageBean,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 首先获取前端传递过来的参数rid
        String ridStr = request.getParameter("rid");

        //2 调用service查询出来route的详情
        Route route = routeService.findOne(ridStr);

        //3 返回前端 json 串
        this.writeValue(route, response);
        //
    }

    /**
     * 根据 rid uid判断是否收藏了当前的route
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //1从request中取出参数 rid,
        String ridStr = request.getParameter("rid");
        //2.从session中取出user信息
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }
        //3.从service层获取是否收藏了
        boolean isFavorate = favorateService.isFavorate(ridStr, uid);

        //将结果返回前端，json
        this.writeValue(isFavorate,response);

    }

    public void addFavorate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数rid
        String ridStr = request.getParameter("rid");
        if(ridStr==null || "null".equals(ridStr) || ridStr.length() == 0){
            return ; //filter out the occassion that the ridStr do not have the valide data.
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null)
            return;
        int uid = user.getUid();
        //2.调用favoriteService里面的addFavorite方法

        routeService.addFavorite(ridStr, uid);


    }

}
