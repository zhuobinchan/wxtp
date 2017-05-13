package com.ehighsun.wxtp.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

@Async
public class SendEmailUtil {

	public static void sendEmailByHtml(String toEmail, String subject,
			String html) {

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost("smtp.163.com");

		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

		// 设置收件人，寄件人
		try {
			messageHelper.setTo(toEmail);
			messageHelper.setFrom("ehighsun01@163.com");
			messageHelper.setSubject(subject);
			// true 表示启动HTML格式的邮件
			messageHelper.setText(html, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		senderImpl.setUsername("ehighsun01@163.com"); // 根据自己的情况,设置username
		senderImpl.setPassword("ml21233qq"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件

		try {
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("邮件发送成功..");
	}

}
