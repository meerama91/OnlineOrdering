<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CMPE275_TermProject_Group5:Registration</title>
</head>
<style>
body {
	background-image: url('resources/images/login.jpg');
	background-size: 100%;
	background-repeat: no-repeat;
	color: #000000;
	font-family: Papyrus, fantasy;
	font-weight: bold;
}

#myBackground {
	background-color: rgba(255, 255, 255, 0.5);
	border-radius: 4px;
	color: inherit;
	width: 120%;
	height: 115%;
}

#title {
	width: 330px;
	height: 26px;
	padding-top: 15px;
	text-transform: uppercase;
	letter-spacing: 2px;
	text-align: center;
	font-size: 14px;
	font-family: Papyrus, fantasy;
}

form {
	width: 335px;
}

.col1 {
	text-align: right;
	width: 135px;
	height: 31px;
	margin: 0;
	float: left;
	margin-right: 2px;
	text-transform: uppercase;
	letter-spacing: 1px;
	font-family: Papyrus, fantasy;
	font-size: 12px;
}

.col2 {
	width: 195px;
	height: 40px;
	display: block;
	float: left;
	margin: 0;
}

.col2comment {
	width: 195px;
	height: 98px;
	margin: 0;
	display: block;
	float: left;
}

.col1comment {
	text-align: right;
	width: 135px;
	height: 98px;
	float: left;
	display: block;
	margin-right: 2px;
}

div.row {
	clear: both;
	width: 335px;
}

.input {
	margin: 4px 0 5px 8px;
	padding: 1px;
	border: 1px solid #8595B2;
	height: 30px;
}

.textarea {
	border: 1px solid #8595B2;
	margin: 4px 0 5px 8px;
}

.button:hover {
	background-color: #031E2A;
	color: #7EB2C8;
}

.mybutton {
	margin-left: 140px;
}

.button {
	background-color: #DF6F12;
	color: black;
	border: 1px solid #DF6F12;
	border-radius: 4px;
	padding: 5px 12px;
	font-size: 16px;
}

.bodycontent {
	margin: auto;
	margin-top: 150px;
	width: 25%;
}

.exist {
	margin-left: 155px;
}

.mybutton {
	background-color: #DF6F12;
	color: black;
	border: 1px solid #DF6F12;
	border-radius: 4px;
	padding: 5px 12px;
	font-size: 16px;
	width: 75px;
}

.mybutton:hover {
	background-color: #031E2A;
	color: #7EB2C8;
}
</style>

<body>
	<div class="bodycontent">
		<div id="myBackground">
			<div style="margin-left: 40px;">
				<br>
				<br>
				<div id="title">
					<b>CMPE275_TermProject_Group5</b>
				</div>
				<div id="title" style="font-size: 14px; margin-left: 20px;">
					<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Login</b>
				</div>
				<br>
				<form method="post" action="userLogin">
					<br>
					<div class="row">
						<label class="col1"><b> Email:</b>&nbsp;&nbsp;</label> <span
							class="col2"><input type="text" name="email" value=""
							required /></span>
					</div>
					<div class="row">
						<label class="col1"> <b>Password:</b>&nbsp;&nbsp;
						</label> <span class="col2"><input type="password" name="password"
							value="" required /></span>
					</div>
					<br> <br>
					<br> <input class="mybutton" type="submit" name="login"
						value="Login"> <br>
					<br>
					<br>
					<div class="row">
						<span
							style="margin-left: 150px; font-family: Papyrus, fantasy; font-size: 12px;">New
							user?</span>&nbsp;&nbsp; <input type="button" class=button id="register"
							name="register" value="Register"
							onclick="location.href = 'http://127.0.0.1:8080/OnlineOrdering/register';">
					</div>
					<br>
					<br>
				</form>

			</div>
		</div>
	</div>

</body>
</html>
