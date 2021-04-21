package dpcafc;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMailTest {
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
		mailSender.setUsername(username); // 根据自己的情况,设置username
		mailSender.setPassword(password); // 根据自己的情况, 设置password
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
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
		Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(p);
		mailSender.send(mime);
	}

	public static void main(String[] args) throws Exception {
		String username="admin@test.com.cn";
		String password="Lt949389";
		String host="smtp.exmail.qq.com";//域名或ip
		String port="465"; 
		String fromAddress="admin@test.com.cn";
		String text="这是测试邮件"; 
		String subject="2021-04-13发送"; 
		String[] to=new String[] {"admin@test.com.cn"}; 
		String[] cc=null; 
		String[] bcc=null;
		sendMailCore(username, password, host, port, fromAddress, text, subject, to, cc, bcc);
		System.out.println("发送完成");
	}
}
