<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Cmpe275_Term_Project_5 : Add New Menu Item</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<style>
body {
	background-image:
		url('resources/images/deletemenu.jpg');
	font-family: Papyrus, fantasy;
	font-weight: bold;
	background-size: 100%;
	background-repeat: no-repeat;
	color: #000000;
}

#title {
	width: 350px;
	height: 26px;
	padding-top: 45px;
	text-transform: uppercase;
	letter-spacing: 2px;
	text-align: center;
	font-size: 22px;
	margin-left: 135px;
}

form {
	width: 335px;
}

.col1 {
	text-align: right;
	width: 170px;
	height: 31px;
	margin: 0;
	float: left;
	margin-right: 2px;
	text-transform: uppercase;
	letter-spacing: 1px;
	font-size: 10px;
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
	width: 435px;
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

.button {
	margin-left: 100px;
	background-color: #7EB2C8;
	color: black;
	border: 1px solid #031E2A;
	border-radius: 6px;
	padding: 5px 12px;
	font-size: 18px;
}

.bodycontent {
	margin: auto;
	margin-top: 20px;
	width: 30%;
	font-size: 14px;
}

.footer {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0;
	padding: 1rem;
	background-color: #e6f3ff;
	text-align: center;
	align: center;
}

.mybutton {
	background-color: #7EB2C8;
	color: black;
	border: 1px solid #031E2A;
	border-radius: 4px;
	padding: 5px 12px;
	font-size: 14px;
}

.mybutton:hover {
	background-color: #031E2A;
	color: #7EB2C8;
}

body {
	background-size: 100%;
	color: #000000;
	background-image:
		url('resources/images/deletemenu.jpg');
	background-repeat: no-repeat;
	background-position: 10% 50%;
	background-attachment: fixed;
}

#myBackground {
	border-radius: 4px;
	width: 40%;
	height: 60%;
	margin-left: 60%;
}

.col2 {
	width: 195px;
	height: 31px;
	display: block;
	float: left;
	margin: 0;
}

.mybutton {
	background-color: #DF6F12;
	color: black;
	border: 1px solid #DF6F12;
	border-radius: 4px;
	padding: 5px 12px;
	font-size: 16px;
	width: 120px;
	text-align: center;
}

.mybutton:hover {
	background-color: #031E2A;
	color: #7EB2C8;
}

.bodycontent {
	margin: auto;
	margin-top: 80px;
	width: 50%;
}

#custom-bootstrap-menu.navbar-default {
	font-size: 17px;
	background-color: rgba(223, 111, 18, 1);
	border-bottom-width: 1px;
}

#custom-bootstrap-menu.navbar-default .navbar-nav>li>a {
	color: rgba(0, 0, 0, 1);
	background-color: rgba(223, 111, 18, 1);
}

#custom-bootstrap-menu.navbar-default .navbar-nav>li>a:hover,
	#custom-bootstrap-menu.navbar-default .navbar-nav>li>a:focus {
	color: rgba(51, 51, 51, 1);
	background-color: rgba(223, 111, 18, 1);
}

#custom-bootstrap-menu.navbar-default .navbar-nav>.active>a,
	#custom-bootstrap-menu.navbar-default .navbar-nav>.active>a:hover,
	#custom-bootstrap-menu.navbar-default .navbar-nav>.active>a:focus {
	color: rgba(0, 0, 0, 1);
	background-color: rgba(209, 93, 16, 1);
}

#custom-bootstrap-menu.navbar-default .navbar-toggle {
	border-color: #d15d10;
}

#custom-bootstrap-menu.navbar-default .navbar-toggle:hover,
	#custom-bootstrap-menu.navbar-default .navbar-toggle:focus {
	background-color: #d15d10;
}

#custom-bootstrap-menu.navbar-default .navbar-toggle .icon-bar {
	background-color: #d15d10;
}

#custom-bootstrap-menu.navbar-default .navbar-toggle:hover .icon-bar,
	#custom-bootstrap-menu.navbar-default .navbar-toggle:focus .icon-bar {
	background-color: #df6f12;
}

hr {
	display: block;
	height: 1px;
	border: 0;
	border-top: 1px solid #ccc;
	margin: 1em 0;
	padding: 0;
}

input {
	font-size: 14px;
}
</style>

</head>
<body>
	<!---navbar----->
	<div id="custom-bootstrap-menu"
		class="navbar navbar-default navbar-fixed-top" role="navigation"
		style="background: #df6f12; height: 70px;">
		<div class="container-fluid"
			style="margin-top: 15px; font-size: 20px; margin-right: 12px;">
			<div class="collapse navbar-collapse navbar-menubuilder">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/OnlineOrdering/AdminHome">Home</a></li>
					<li><a href="/OnlineOrdering/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!---navbar----->


	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script>
		$("document").ready(function() {
			$("#fileUpload").change(function() {
				var filesize = this.files[0].size;
				if (filesize > 1024 * 1024) {
					alert('Image size too big, max is 1MB');
					$("#fileUpload").val('');
				}
			});
		});
	</script>







	<div class="bodycontent">
		<div id="myBackground">
			<div id="title">Add an Item</div>
			<br>
			<br>
			<div style="margin-left: 150px; width: 380px;">
				<hr>
			</div>
			<form method="post" action="addItem" enctype="multipart/form-data">


				<div class="bodycontent" style="margin-top: 25px;">
					<br>
					<div class="row">
						<label class="col1">Item Name: &nbsp;&nbsp;</label> <span
							class="col2"><input type="text" name="name"
							placeholder="enter item name" required /></span>
					</div>

					<div class="row">
						<label class="col1">Category: &nbsp;&nbsp;</label> <span
							class="col2"><select name="category" style="width: 175px;">
								<option name="drink" value="drink">Drink</option>
								<option name="appetizer" value="appetizer">Appetizer</option>
								<option name="maincourse" value="maincourse">Main
									course</option>
								<option name="desert" value="desert">Desert</option>
						</select></span>
					</div>

					<div class="row">
						<label class="col1">Picture: &nbsp;&nbsp;</label> <span
							class="col2"><input type="file" id="fileUpload"
							name="fileUpload" size="500000" style="width: 165x;" required />
					</div>
					<div class="row">
						<label class="col1">Unit Price: &nbsp;&nbsp;</label><span
							class="col2"> <input type="text" name="unitprice"
							placeholder="enter price for 1 item" required /></span>
					</div>
					<div class="row">
						<label class="col1">Calories: &nbsp;&nbsp;</label> <span
							class="col2"><input type="text" name="calories"
							placeholder="enter calories in item" required /></span>
					</div>
					<div class="row">
						<label class="col1">Preparation Time (in mins):
							&nbsp;&nbsp;</label> <span class="col2" style="width: 190px;"><input
							type="text" name="preptime"
							placeholder="enter preparation time (in mins)" required /></span>
					</div>
					<br>
					<br>
					<div class="row">
						<input type="submit" class=mybutton value="Add"
							style="margin-left: 172px; width: 70px;" required>
					</div>


				</div>
			</form>
			<br>
			<br>
			<br>
			<br>

		</div>
	</div>
</body>
</html>
