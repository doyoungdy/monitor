package com.going.storm.monitor.service;

public interface MailService {
	
	void sendSimpleMail(String to, String[] cc, String subject, String content);

}
