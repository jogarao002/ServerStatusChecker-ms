package com.intellect.serverstatuschecker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String[] to, String subject, String body) throws MessagingException {
		
		try {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body, true);
	        javaMailSender.send(mimeMessage);
	        return true;
	    } catch (MessagingException e) {
	        e.printStackTrace(); 
	    }
		return false;
	}

}