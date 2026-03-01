package com.tmf.servlets;

import jakarta.mail.PasswordAuthentication;
import java.util.Properties;
import jakarta.mail.Authenticator;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtil {

	public EmailUtil() {
		super();
	}

	private static final String FROM_EMAIL = "srihima9199@gmail.com";
	private static final String PASSWORD = "dzxcapzzcmhiemf";

	public static void sendEmail(String toEmail, String subject, String body) {

		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(FROM_EMAIL, "Hire A Driver Team"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Email sent successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}