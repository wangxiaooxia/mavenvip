package com.springboot01.springboot01;



import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.Properties;

public class TestMail {
    public static void main(String[] args) throws Exception {

        sendMail("rtlconservice","1603826793@qq.com","","测试","是否可发送邮件",null, new String[0]);

    }


    public static void sendMail(String senderName, String to, String cc, String subject, String content, byte[][] bytes, String[] names) throws MessagingException, IOException {

        // 属性对象
        Properties properties = properties();
        // 环境信息
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 在session中设置账户信息，Transport发送邮件时会使用W
                return new PasswordAuthentication("rtlconservice", "Wsxz@1234");
            }
        });

        //邮件
        MimeMessage msg = new MimeMessage(session);
        //设置主题
        msg.setSubject(subject);
        //发件人，注意中文的处理
        msg.setFrom(new InternetAddress("\"" + MimeUtility.encodeText(senderName) + "\"<" + "" + ">"));
        //设置邮件回复人
//        msg.setReplyTo(new Address[]{new InternetAddress("harry.hu@derbysoft.com")});

        msg.setRecipients(Message.RecipientType.TO, to);
        if (cc != null && "" != "") {
            msg.setRecipients(Message.RecipientType.CC, cc);
        }
        //整封邮件的MINE消息体
        MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
        //设置邮件的MINE消息体
        msg.setContent(msgMultipart);

        // 装载附件
        if (bytes != null && names != null) {
            for (int i = 0; i < bytes.length; i++) {
                MimeBodyPart attch = new MimeBodyPart(); // 附件
                msgMultipart.addBodyPart(attch);         // 将附件添加到MIME消息体中
                ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes[i], "text/data"); //数据源
                attch.setDataHandler(new DataHandler(dataSource));
                attch.setFileName(names[i]);
            }
        }

        //html代码部分
        MimeBodyPart htmlPart = new MimeBodyPart();
        msgMultipart.addBodyPart(htmlPart);
        //html代码
        htmlPart.setContent(content, "text/html;charset=utf-8");
        //发送邮件
        Transport.send(msg, msg.getAllRecipients());

    }

    protected static Properties properties() {
        // 属性对象
        Properties properties = new Properties();
        // 开启debug调试 ，打印信息
        properties.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        properties.setProperty("mail.smtp.auth", "true");
        // 发送服务器端口，可以不设置，默认是25
        properties.setProperty("mail.smtp.port", "25");
        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", "smtp");
        // 设置邮件服务器主机名
        properties.setProperty("mail.host","127.0.0.1");

        return properties;
    }

}
