package com.sumu.acebank.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sumu.acebank.database.UserDao;
import com.sumu.acebank.model.UserModel;

@WebServlet(name="Account" ,urlPatterns = "/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String password1 = request.getParameter("pass1");
		
		HttpSession session = request.getSession();
		
		try {
			UserModel model = new UserModel();
			model.setPassword(password1);
			model.setAccountNumber((int) session.getAttribute("AccountNumber"));
			
			UserDao userDao = new UserDao(model);
			if(userDao.changePassword()) {
				response.sendRedirect("/AceBank/Home.jsp");
			}else {
				response.sendRedirect("/AceBank/Account-Fail.html");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
