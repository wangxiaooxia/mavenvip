package com.springboot01.springboot01;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class TestMail2 {
    private static String from = "rtlconservice@dpcafc.com";// 用于给用户发送邮件的邮箱
    private static String password = "Wsxz@1234"; // 邮箱的密码
    private static String host = "119.253.85.49";// 发送邮件的服务器地址
    public static void sendMail(String to, String title, String content) {
        try {

            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
//            prop.setProperty("mail.port", "8888");
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 在session中设置账户信息，Transport发送邮件时会使用W
                    return new PasswordAuthentication("rtlconservice", "Wsxz@1234");
                }
            });
            session.setDebug(true);
            Transport ts = session.getTransport();
            ts.connect(host, from, password);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");
            message.saveChanges();
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // 参数为接收邮件的邮箱地址和邮件主题和内容
        String nowDate = new Date().toString();
        sendMail("wangxx16@yusys.com.cn", "java实现邮件开发", "利用改方法可以实现邮件发送！！" + nowDate);
    }


}
