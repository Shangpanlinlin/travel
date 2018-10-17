package cn.itcast.travel.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//to solve web site encoding issue.

@WebFilter("/*")  //instead of the web.xml, do not forget it
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 1. change the type of request and response
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 2. check the method of request
        String method = request.getMethod();
        if("post".equalsIgnoreCase(method)){
            //if post method, we need to set character endcoding to make sure request body content will be encoded correct for chinese word
            request.setCharacterEncoding("utf-8");
        }
        // 3. set the response encoding
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
    }
}
