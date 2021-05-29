package com.sumu.acebank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumu.acebank.database.UserDao;
import com.sumu.acebank.model.UserModel;

/**
 * Servlet implementation class SignUP
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {//servlet
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_Name");
		long aadharNumber = Long.parseLong(request.getParameter("aadhar_number"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int accountNumber = ThreadLocalRandom.current().nextInt(10000000, 99999999); //Generate 10 digit random number
		String customerId = getRandomAlphaNumbericID(aadharNumber, firstName);
		

		HttpSession session = request.getSession();

		try {

			UserModel model = new UserModel();

			model.setAadharNumber(aadharNumber);
			model.setAccountNumber(accountNumber);
			model.setCustomerId(customerId);
			model.setEmail(email);
			model.setPassword(password);
			model.setFirstName(firstName);
			model.setLastName(lastName);
			model.setBalance(0);

			UserDao userDao = new UserDao(model);
			
			if (userDao.signup()) {

				if (model.getUserDetailsList().isEmpty()) {
					session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
				} else {
					session.setAttribute("transactionDetailsList", model.getUserDetailsList());
				}

				session.setAttribute("AccountNumber", model.getAccountNumber());
				session.setAttribute("FirstName", model.getFirstName());
				session.setAttribute("balance", model.getBalance());
				session.setAttribute("customerID", model.getCustomerId());
				response.sendRedirect("/AceBank/Home.jsp");
			} else {
				response.sendRedirect("/AceBank/Signup-Fail.html");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * Uses aadharNumber and firstName to generate a Customer ID
	 * 
	 * @param aadharNumber
	 * @param firstName
	 * @return alpha numeric 6 digit string
	 */
	
	private String getRandomAlphaNumbericID(long aadharNumber, String firstName) {
		
		var aadharString = String.valueOf(aadharNumber);
		
		var stringBuilder = new StringBuilder();
		for(int i = 0; i < 6; i++) {
			
			if(i < 3) {
				stringBuilder.append(aadharString.charAt(ThreadLocalRandom.current().nextInt(aadharString.length())));
			}
			if(i >= 3) {
				stringBuilder.append(firstName.charAt(ThreadLocalRandom.current().nextInt(firstName.length())));
			}
		}
		
		return stringBuilder.toString();
		
	}

}
