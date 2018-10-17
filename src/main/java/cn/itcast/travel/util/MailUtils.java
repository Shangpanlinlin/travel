package cn.itcast.travel.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {
    private static final String USER = "379336388@qq.com"; // 发件人称号，同邮箱地址
    private static final String PASSWORD = "radisnimghxkcaef"; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.qq.com");
            email.setSslSmtpPort("587");

            email.addTo(to, "John Doe");
            email.setFrom(USER, "Me");
            email.setSubject(title);

            email.setAuthenticator(new DefaultAuthenticator(USER, PASSWORD));
            email.setSSLOnConnect(true);


            // set the html message
            email.setHtmlMsg(text);
            email.setCharset("utf-8");

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            // send the email
            email.send();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        boolean result = MailUtils.sendMail("applepll1023@163.com","你好，这是一封测试邮件，无需回复。","测试邮件");
        if(result)
         System.out.println("发送成功");
    }



}
