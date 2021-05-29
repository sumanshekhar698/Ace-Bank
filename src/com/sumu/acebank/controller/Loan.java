package com.sumu.acebank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumu.acebank.database.UserDao;
import com.sumu.acebank.model.UserModel;

@WebServlet(name="Loan", urlPatterns = "/Loan")
public class Loan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int accountNumber = (int) session.getAttribute("AccountNumber");

		String link = request.getParameter("link");
		System.out.println("from loan" + link);

		try {
			UserModel model = new UserModel();
			model.setAccountNumber(accountNumber);
			
			UserDao userDao = new UserDao(model);
			
			if (userDao.applyLoan()) {
				session.setAttribute("email", model.getEmail());
				response.sendRedirect("/AceBank/LoanOptions.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
