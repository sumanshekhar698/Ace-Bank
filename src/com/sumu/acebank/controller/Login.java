package com.sumu.acebank.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumu.acebank.database.UserDao;
import com.sumu.acebank.model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", urlPatterns = "/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String customerId = request.getParameter("customerID");
		String password = request.getParameter("password");
		
		System.out.println(customerId + " Customer ID from Login Servlet");

		HttpSession session = request.getSession(true);

		try {

			UserModel model = new UserModel();
			model.setCustomerId(customerId);
			model.setPassword(password);

			UserDao userDao = new UserDao(model);

			if (userDao.login()) {
				session.setAttribute("AccountNumber", model.getAccountNumber());
				session.setAttribute("FirstName", model.getFirstName());
				session.setAttribute("balance", model.getBalance());
				session.setAttribute("email", model.getEmail());
				session.setAttribute("customerID", model.getCustomerId());

				if (userDao.statement().isEmpty()) {
					session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
				} else {
					session.setAttribute("transactionDetailsList", model.getUserDetailsList());
				}
				response.sendRedirect("/AceBank/Home.jsp");

			} else {
				response.sendRedirect("/AceBank/Login-Fail.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
