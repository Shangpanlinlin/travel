
package cn.itcast.travel.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "/checkCode" , urlPatterns = "/checkCode")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. tell the browser do not cache the result of this request
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //2. draw the image in memory
        int width = 80;
        int height = 30;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //3. get the pen
        Graphics g = bufferedImage.getGraphics();
        //4.set the color of graphics
        g.setColor(Color.gray);
        g.fillRect(0,0,width,height);

        //5.generate four code
        String code = getCheckCode();

        //6. save the code in session
        request.getSession().setAttribute("CHECKCODE_SERVER", code);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体",Font.BOLD,24));
        g.drawString(code, 15,25);

        //6. write the image in memory into response
        ImageIO.write(bufferedImage, "PNG",response.getOutputStream());
    }

    private String getCheckCode() {
        String range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        int len = range.length();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 4; i++){
            sb.append(range.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }
}



