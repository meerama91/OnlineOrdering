package com.cmpe275.OnlineOrdering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Notify implements Runnable {

	private String email, body;

	
	private MailSender mailOtp;

	public Notify(String email, String body, MailSender mailOtp) {
		this.email = email;
		this.body = body;
		this.mailOtp=mailOtp;
	}

	/**
	 * send notification mails to the user as a separate thread
	 * 
	 * @author Meera
	 */
	@Override
	public void run() {

		SimpleMailMessage verifyMail = new SimpleMailMessage();
		verifyMail.setFrom("group5.275@gmail.com");
		verifyMail.setTo(email);
		verifyMail.setSubject("Online Ordering Notification");
		verifyMail.setText(body);
		mailOtp.send(verifyMail);
		System.out.println("mail sent!");

	}

}
