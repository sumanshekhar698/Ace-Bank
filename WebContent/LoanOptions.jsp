<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css"
	href="/AceBank/css/navbar-home.css">
<link rel="stylesheet" type="text/css" href="/AceBank/css/loan.css">
<title>Select Loans</title>
</head>

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
				<li><a href="Home.jsp">Home</a></li>
				<li><a href="#">Account</a></li>
				<li><a href="Logout" class="login-btn">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="toolbar-space"></div>
	<%
	session = request.getSession();
	String msg = (String) session.getAttribute("email");
	%>

	<main>
		<section class="title">
			<h1>Choose a loan</h1>
			<p>
				You'll receive a mail at "<%
			out.print(msg);
			%>" with the details once
				you select a loan.
			</p>
		</section>

		<div class="container">
			<div class="cards-container">
				<div class="card card-one">
					<div class="img-container">
						<img src="/AceBank/images/home.svg" alt="home loan icon">
					</div>
					<div class="card-content">
						<h3>Home Loan</h3>
						<p>Up to ₹5,00,00,000</p>
					</div>
					<a href="Loan">Send Details</a>
				</div>
			</div>
			<!--  -->
			<div class="cards-container">
				<div class="card card-two">
					<div class="img-container">
						<img src="/AceBank/images/piggy.svg" alt="personal loan icon">
					</div>
					<div class="card-content">
						<h3>Personal Loan</h3>
						<p>Up to ₹15,00,000</p>
					</div>
					<a href="Loan">Send Details</a>
				</div>
			</div>
			<!--  -->
			<div class="cards-container">
				<div class="card card-three">
					<div class="img-container">
						<img src="/AceBank/images/student.svg" alt="home icon">
					</div>
					<div class="card-content">
						<h3>Student Loan</h3>
						<p>Up to ₹15,00,000</p>
					</div>
					<a href="Loan">Send Details</a>
				</div>
			</div>
			<!--  -->
			<div class="cards-container">
				<div class="card card-four">
					<div class="img-container">
						<img src="/AceBank/images/garage.svg" alt="home icon">
					</div>
					<div class="card-content">
						<h3>Car Loan</h3>
						<p>Up to ₹15,00,000</p>
					</div>
					<a href="Loan">Send Details</a>
				</div>
			</div>
			<!--  -->
			<div class="cards-container">
				<div class="card card-five">
					<div class="img-container">
						<img src="/AceBank/images/bride-and-groom.svg" alt="home icon">
					</div>
					<div class="card-content">
						<h3>Marriage Loan</h3>
						<p>Up to ₹5,00,00,000</p>
					</div>
					<a href="Loan">Send Details</a>
				</div>
			</div>
			<!--  -->
		</div>
	</main>
</body>

</html>