package com.springboot01.springboot01;


import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class MyTest {
	static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	/**
	 * 发邮件
	 * 
	 * @param username    邮箱账号
	 * @param password    邮箱密码
	 * @param host        邮件服务器ip
	 * @param port        邮件服务器端口
	 * @param fromAddress 发件人邮箱
	 * @param text        邮件内容
	 * @param subject     邮件主题
	 * @param to          收件人
	 * @param cc          抄送人
	 * @param bcc         密送人
	 * @throws Exception
	 * @date 2021年4月13日 上午9:19:13
	 */
	public static void sendMailCore(String username, String password, String host, String port, String fromAddress,
			String text, String subject, String[] to, String[] cc, String[] bcc) throws Exception {
		System.out.println("开始发送邮件");
		MimeMessage mime = mailSender.createMimeMessage();
		// 环境信息
		Properties properties = new Properties();
		// 开启debug调试 ，打印信息
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "false");
		// 发送服务器端口，可以不设置，默认是25
		properties.setProperty("mail.smtp.port", "8888");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host","127.0.0.1");
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 在session中设置账户信息，Transport发送邮件时会使用W
				return new PasswordAuthentication("rtlconservice", "Wsxz@1234");
			}
		});
		mailSender.setSession(session);
//		mailSender.setUsername(username); // 根据自己的情况,设置username
//		mailSender.setPassword(password); // 根据自己的情况, 设置password
//		mailSender.setHost(host);
//		mailSender.setPort(Integer.parseInt(port));
//		mailSender.setProtocol("smtp");
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
		helper.setFrom(fromAddress);// 发件人
		if(to==null) {
			System.out.println("无收件人，不发送邮件");
			return;
		}
		helper.setTo(to);// 收件人
		if(cc!=null) {
			helper.setCc(cc);// 抄送
		}
		if(bcc!=null) {
			helper.setBcc(bcc);// 暗送
		}
		helper.setSubject(subject);// 邮件主题
		helper.setText(text, true);// true表示设定html格式
		File file = new File("F:\\平时工作\\行政区划.xlsx"); //发送附件，邮件里面含有附件时，需要添加附件
		helper.addAttachment(file.getName(),file);
//		Properties p = new Properties();
//        p.setProperty("mail.smtp.timeout", "25000");
//        p.setProperty("mail.smtp.auth", "true");
//        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        p.setProperty("mail.smtp.starttls.enable","true");
//        mailSender.setJavaMailProperties(p);
		mailSender.send(mime);
	}

	public static void main(String[] args) throws Exception {
//		String username="rtlconservice";
//		String password="Wsxz@1234";
//		String host="127.0.0.1";//域名或ip
//		String port="8888";
//		String fromAddress="rtlconservice@dpcafc.com";
//		String text="这是测试邮件";
//		String subject="2021-04-13发送";
//		String[] to=new String[] {"1603826793@qq.com"};
//		String[] cc=null;
//		String[] bcc=null;
//		sendMailCore(username, password, host, port, fromAddress, text, subject, to, cc, bcc);
//		System.out.println("发送完成");

		Properties properties = new Properties();
		// 开启debug调试 ，打印信息
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "false");
		// 发送服务器端口，可以不设置，默认是25
		properties.setProperty("mail.smtp.port", "8888");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host","127.0.0.1");

		Session session = Session.getInstance(properties,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("rtlconservice", "Wsxz@1234");
			}
		});
		session.setDebug(true);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("rtlconservice@dpcafc.com"));
		InternetAddress[] address = {new InternetAddress("1603826793@qq.com")};
		msg.setRecipients(Message.RecipientType.TO, address);
		msg.setSubject("JavaMail APIs Test");
		msg.setSentDate(new Date());
		msg.setText("This is a message body.\nHere's the second line.");

		Transport.send(msg);
	}
}
