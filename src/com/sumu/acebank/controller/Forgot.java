package com.sumu.acebank.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumu.acebank.database.UserDao;
import com.sumu.acebank.model.UserModel;

@WebServlet(name = "Forgot", urlPatterns = "/Forgot")
public class Forgot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EMAIL_ADDRESS = "ace.bank.dev@gmail.com";
	private static final String PASSWORD = "aPPLEfresh";

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fromEmail = EMAIL_ADDRESS;
		String password = PASSWORD;
		String subject = "Password details";
		String toEmail = request.getParameter("email");

		HttpSession session = request.getSession();
		session.setAttribute("email", toEmail);

		try {

			UserModel model = new UserModel();
			model.setEmail(toEmail);

			UserDao userDao = new UserDao(model);

			if (userDao.forgotPassword()) {
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
				prop.put("mail.smtp.port", 587);
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.starttls.enable", "true");

				Session sessionMail = Session.getDefaultInstance(prop, new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(fromEmail, password);
					}

				});

				MimeMessage mesg = new MimeMessage(sessionMail);

				try {
					mesg.setFrom(new InternetAddress(fromEmail));
					mesg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
					mesg.setSubject(subject);
					mesg.setText("Your password is: " + model.getPassword());

				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Transport.send(mesg);
				} catch (Exception e) {
					e.printStackTrace();
				}

				response.sendRedirect("/AceBank/Login.html");
			} else {
				response.sendRedirect("/AceBank/Forgot-Fail.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
