package com.sumu.acebank.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.sumu.acebank.model.UserDetails;
import com.sumu.acebank.model.UserModel;
import com.sumu.acebank.utils.ConnectionManager;

public class UserDao {
	private UserModel userModel;

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;

	public UserDao(UserModel model) throws SQLException {
		con = ConnectionManager.getInstance().getConnection();
		userModel = model;
	}

	/**
	 * login() used in the Login Servlet class This is used to check if the customer
	 * ID and password are correct. It will also get the required fields which will
	 * be used in the current session.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean login() throws SQLException {
		String s = "select ACCOUNT_NO, FIRST_NAME, EMAIL, Balance from BANKUSERS"
				+ " where CUSTOMER_ID=? and PASSWORD=?";
		pstmt = con.prepareStatement(s);

		pstmt.setString(1, userModel.getCustomerId());
		pstmt.setString(2, userModel.getPassword());
		System.out.println(userModel.getCustomerId() + " From userDao.login()");
		res = pstmt.executeQuery();

		if (res.next() == true) {
			userModel.setAccountNumber(res.getInt(1));
			userModel.setFirstName(res.getString(2));
			userModel.setEmail(res.getString(3));
			userModel.setBalance(res.getInt(4));
			return true;
		}

		System.out.println("FAIL in userDao.login() " + userModel.getAccountNumber());
		return false;
	}

	/**
	 * signup() is used in the SignUp Servlet class Used to make a new entry in the
	 * database for the new User Balance is set to 0 for the new user
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean signup() throws SQLException {

		String s = "insert into BANKUSERS (ACCOUNT_NO, CUSTOMER_ID, "
				+ "FIRST_NAME, LAST_NAME, AADHAR_NO, EMAIL, PASSWORD, " + "Balance)" + "values (?,?,?,?,?,?,?,?)";

		pstmt = con.prepareStatement(s);

		pstmt.setInt(1, userModel.getAccountNumber());
		pstmt.setString(2, userModel.getCustomerId());
		pstmt.setString(3, userModel.getFirstName());
		pstmt.setString(4, userModel.getLastName());
		pstmt.setLong(5, userModel.getAadharNumber());
		pstmt.setString(6, userModel.getEmail());
		pstmt.setString(7, userModel.getPassword());
		pstmt.setInt(8, 0); // Set balance to 0 for new accounts

		int x = pstmt.executeUpdate();
		return x > 0;

	}

	/**
	 * forgotPassword() is used in the Forgot Servlet class. This method will use a
	 * valid email address entered by the user to get the password, password will
	 * then be sent to that email address.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean forgotPassword() throws SQLException {

		String s = "select PASSWORD from BANKUSERS where EMAIL =?";

		pstmt = con.prepareStatement(s);
		pstmt.setString(1, userModel.getEmail());

		res = pstmt.executeQuery();
		if (res.next()) {
			userModel.setPassword(res.getString(1));
			return true;
		}

		return false;
	}

	/**
	 * Used in Home Servlet deposit() is used to set the balance amount for the
	 * current user.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean deposit() throws SQLException {
		String s = "update BANKUSERS set BALANCE=? where ACCOUNT_NO = ?";

		pstmt = con.prepareStatement(s);
		pstmt.setInt(2, userModel.getAccountNumber());

		int balance = userModel.getBalance();
		int deposit = userModel.getDeposit();

		balance += deposit;
		pstmt.setInt(1, balance);

		int x = pstmt.executeUpdate();

		int id = getID() + 1;
		System.out.println(id + "ID in deposit");

		String statement = "insert into USERGETSTATEMENT values (?,?,?,?,?,?)";
		pstmt = con.prepareStatement(statement);

		pstmt.setInt(1, userModel.getAccountNumber());
		pstmt.setInt(2, userModel.getAccountNumber()); // Since its from the same account
		if (deposit < 0) {
			pstmt.setInt(3, deposit * -1); // Negative to positive
			pstmt.setInt(4, 0);
		} else {
			pstmt.setInt(3, 0);
			pstmt.setInt(4, deposit);
		}
		pstmt.setInt(5, balance);
		pstmt.setInt(6, id);
		pstmt.executeUpdate();

		userModel.setBalance(balance); // update balance

		return x > 0;
	}

	/**
	 * Used in Home Servlet
	 * 
	 * transfer() is used to send the amount from the current user's balance to
	 * another user's balance
	 * 
	 * @return true if successful
	 * @throws SQLException
	 */
	public boolean transfer() throws SQLException {
		/*
		 * Subtract balance from the sender's account first
		 */
		String s = "update BANKUSERS set BALANCE=? where ACCOUNT_NO = ?";

		int balance = userModel.getBalance();
		int withdraw = userModel.getWithdraw();

		balance -= withdraw; // Subtract
		userModel.setBalance(balance); // update balance

		pstmt = con.prepareStatement(s);
		pstmt.setInt(1, balance);
		pstmt.setInt(2, userModel.getAccountNumber());

		int x = pstmt.executeUpdate();

		int id = getID() + 1;

		// *********************************************
		// update UserGetStatement Table
		String table2 = "insert into USERGETSTATEMENT values (?,?,?,?,?,?)";
//		String table2 = "insert into USERGETSTATEMENT values (?,?,?,?,?,?,?)";

		pstmt = con.prepareStatement(table2);
		pstmt.setInt(1, userModel.getAccountNumber());
		pstmt.setInt(2, userModel.getToAccount());
		pstmt.setInt(3, withdraw);
		// Deposit
		pstmt.setInt(4, 0);
		pstmt.setInt(5, balance);
		pstmt.setInt(6, id);
		
		pstmt.executeUpdate();
		// ********************************************

		if (x > 0) {
			/*
			 * Query the balance from account2 to add the withdraw amount to it
			 */
			String st = "select balance from BANKUSERS where ACCOUNT_NO = ?";
			pstmt = con.prepareStatement(st);
			pstmt.setInt(1, userModel.getToAccount());
			res = pstmt.executeQuery();

			if (res.next()) {
				int add = res.getInt(1); // Adding
				withdraw += add;
				String s1 = "update BANKUSERS set BALANCE=? where ACCOUNT_NO = ?";
				pstmt = con.prepareStatement(s1);
				pstmt.setInt(1, withdraw);
				pstmt.setInt(2, userModel.getToAccount());
				int y = pstmt.executeUpdate();

				// ********************************************
				// update UserGetStatement Table
				String table2a = "insert into USERGETSTATEMENT values (?,?,?,?,?,?)";

				pstmt = con.prepareStatement(table2a);
				pstmt.setInt(1, userModel.getToAccount());
				pstmt.setInt(2, userModel.getAccountNumber());
				pstmt.setInt(3, 0);
				pstmt.setInt(4, withdraw - add);
				pstmt.setInt(5, withdraw);
				pstmt.setInt(6, id);

				int y2 = pstmt.executeUpdate();
				if (y2 > 0)
					System.out.println("Updated table 2 again");
				else
					System.out.println("Fail table 2 again");
				// ********************************************

				return y > 0;
			}

		}

		return false;
	}



	/**
	 * 
	 * Used to get the transaction details, which would be shown in Home.jsp
	 * 
	 * @return
	 * @throws SQLException
	 */

	public List<UserDetails> statement() throws SQLException {
		String statement = "select * from USERGETSTATEMENT where ACCOUNT=? order by id desc";
		pstmt = con.prepareStatement(statement);
		pstmt.setInt(1, userModel.getAccountNumber());

		res = pstmt.executeQuery();
		while (res.next()) {

			userModel.getUserDetailsList()
					.add(new UserDetails(res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5)));
		}

		return userModel.getUserDetailsList();
	}

	/**
	 * getID() returns the biggest ID number from the table for the given account
	 * Number user to increment the id while entering data into the GetUserDetails
	 * table This is used to order the data recived in statement() (order by id)
	 * 
	 * @return return the biggest ID number if it exists in the table, else return 0
	 * @throws SQLException
	 */

	private int getID() throws SQLException {
		String idStatement = "select max(id) from USERGETSTATEMENT where ACCOUNT=? ";
		pstmt = con.prepareStatement(idStatement);
		pstmt.setInt(1, userModel.getAccountNumber());
		res = pstmt.executeQuery();
		if (res.next()) {
			System.out.println(res.getInt(1));
			return res.getInt(1);
		}
		return 0;
	}

	/**
	 * applyLoan() used in the Loan Servlet class used to get The email address
	 * 
	 * @return true if success
	 * @throws SQLException
	 */

	public boolean applyLoan() throws SQLException {
		String s = "select EMAIL from BANKUSERS where ACCOUNT_NO = ?";
		pstmt = con.prepareStatement(s);

		pstmt.setInt(1, userModel.getAccountNumber());
		res = pstmt.executeQuery();

		if (res.next()) {
			userModel.setEmail(res.getString(1));
			return true;
		}

		return false;
	}

	/**
	 * Change password through Account Servlet
	 * 
	 * @return true if update successful
	 * @throws SQLException
	 */
	public boolean changePassword() throws SQLException {
		var s = "update BANKUSERS set PASSWORD = ? where ACCOUNT_NO = ?";

		pstmt = con.prepareStatement(s);
		pstmt.setString(1, userModel.getPassword());
		pstmt.setInt(2, userModel.getAccountNumber());

		var x = pstmt.executeUpdate();

		return x > 0;
	}
}
