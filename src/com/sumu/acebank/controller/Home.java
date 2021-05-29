package com.sumu.acebank.controller;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int addToBalance = 0;
		int toAccount = 0;
		int amount = 0;

		int accountNumber = (int) session.getAttribute("AccountNumber");

		if (request.getParameter("deposit") != null) {
			addToBalance = Integer.parseInt(request.getParameter("deposit"));
		}
		
		// Remove session?
		if (request.getParameter("toAccount") != null) {
			toAccount = Integer.parseInt(request.getParameter("toAccount"));
			session.setAttribute("toAccount", toAccount);
		}
		if (request.getParameter("amount") != null) {
			amount = Integer.parseInt(request.getParameter("amount"));
			session.setAttribute("withdraw", amount);
		}

		try {
			UserModel model = new UserModel();

			model.setDeposit(addToBalance);

			model.setAccountNumber(accountNumber);
			model.setBalance((int) session.getAttribute("balance"));
			
			UserDao userDao = new UserDao(model);

			if (addToBalance != 0) {
				boolean depositCheck = userDao.deposit();
				if (depositCheck) {
					
					if (userDao.statement().isEmpty()) {
						session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
					} else {
						session.setAttribute("transactionDetailsList", model.getUserDetailsList());
					}
					
					addToTransactionDetails(userDao, session, model);
					// Update balance
					request.setAttribute("balance", model.getBalance());
					session.setAttribute("balance", model.getBalance());
					request.getRequestDispatcher("/Home.jsp").forward(request, response);
				}
			}

			if (toAccount != 0 && amount != 0) {
				
				model.setToAccount((int) session.getAttribute("toAccount"));
				model.setWithdraw((int) session.getAttribute("withdraw"));
				boolean transferCheck = userDao.transfer();
				
				if (transferCheck) {
					
					if (userDao.statement().isEmpty()) {
						session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
					} else {
						session.setAttribute("transactionDetailsList", model.getUserDetailsList());
					}
					
					addToTransactionDetails(userDao, session, model);
					// Update balance
					request.setAttribute("balance", model.getBalance());
					session.setAttribute("balance", model.getBalance());
					request.getRequestDispatcher("/Home.jsp").forward(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addToTransactionDetails(UserDao userDao, HttpSession session, UserModel model)
			throws SQLException {


		
	
		
	}

}
