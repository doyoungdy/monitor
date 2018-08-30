package com.going.storm.monitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendSimpleMail(String to, String[] cc, String subject, String content) {
		logger.info("发送邮件{}开始", subject);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("doyoungdy@sina.com");
		mailMessage.setTo(to);
		if (cc != null && cc.length > 0)
			mailMessage.setCc(cc);
		mailMessage.setSubject(subject);
		mailMessage.setText(content);

		try {
			logger.info("邮件内容为：{}", mailMessage);
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件{}失败", subject);
		}
		logger.info("发送邮件{}完成", subject);
	}

}
