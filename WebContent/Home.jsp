<%@page import="com.sumu.acebank.model.UserDetails"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/AceBank/css/navbar-home.css">
<link rel="stylesheet" href="/AceBank/css/home.css">
<title>Home</title>
</head>
<%
	session = request.getSession();
	String msg = (String) session.getAttribute("FirstName");
	int accountNumber = (int) session.getAttribute("AccountNumber");
	String customerID = (String) session.getAttribute("customerID");
%>
<body>
	<header>
		<h1 class="logo-text">
			Ace<span class="bank">Bank</span>
		</h1>
		<!-- Navigation toggle -->
		<input type="checkbox" id="nav-toggle" class="nav-toggle"> <label
			for="nav-toggle" class="nav-toggle-label"> <img
			src="/AceBank/images/menu-24px.svg">
		</label>
		<nav>
			<ul>
			
				<!-- Using Account.html instead of Account, since it would crash with an sql exception if used directly;
					 The class would directly execute without waiting for the form otherwise -->
					 
				<li><a href="Account.html">Account</a></li>
				<li><a href="Logout" class="login-btn">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="toolbar-space"></div>
	<main class="main-container">
		<div class="title-container">
			<h1>
				Hello,
				<%
				out.print(msg);
			%>.
			</h1>
			  <h3>Account Number: <%
			  	out.print(accountNumber);
			  %> | ID: <% out.print(customerID); %></h3>
		</div>
		<div class="balance-transfer-container">
			<!--  -->
			<div class="balance">
				<h2>Your Total Balance</h2>
				<h1>
					<span>₹</span> ${ balance }
				</h1>
				<div class="buttons">
					<form action="Home" method="post">
						<input type="text" name="deposit" placeholder="Deposit" /> <input
							type="submit" name="deposit-button" value="Enter" />
					</form>
					<a href="Loan">Get Loans</a>
				</div>
			</div>
			<!--  -->
			<div class="transfer">
				<h2>Transfer Amount</h2>
				<form action="Home">
					<div>
						<!-- <label>To</label> -->
						<input type="text" name="toAccount" placeholder="Account Number" />
						<!-- <label>Amount</label> -->
						<input type="text" name="amount" placeholder="Amount" />
					</div>
					<input type="submit" value="Send" />
				</form>
			</div>
			<!--  -->
		</div>
		<!--  -->
		<section class="statement">
			<h2>Transactions</h2>
			<div class="fake-table">

				<table class="content-table">
				<thead>
						<tr>
							<th>Account</th>
							<th>Account2</th>
							<th>Withdraw</th>
							<th>Deposit</th>
							<th>Balance</th>
						</tr>
					</thead>
					<%
						List<UserDetails> transactionDetailsList = (List<UserDetails>) session.getAttribute("transactionDetailsList");

									for (UserDetails s : transactionDetailsList) {
					%>
					<tbody>
						<tr>
							<td><%=s.getAccount1()%></td>
							<td><%=s.getAccount2()%></td>
							<td><%=s.getWithdraw()%></td>
							<td><%=s.getDeposit()%></td>
							<td><%=s.getBalance()%></td>
						</tr>
					</tbody>
					<% } %>
				</table>

			</div>
		</section>
	</main>
</body>

</html>