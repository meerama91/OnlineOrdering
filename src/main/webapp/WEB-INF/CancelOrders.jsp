<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CMPE275_TermProject_Group5:Delete Menu Items</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<style>
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

#backgroundImage {
	z-index: 1;
}

#backgroundImage:before {
	content: "";
	position: absolute;
	z-index: -1;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background-image:
		url('https://d3ui957tjb5bqd.cloudfront.net/images/screenshots/products/10/106/106399/crsrxfllostehmuxwzqfkdarpcg0di40toehyl4mzmgrkmy3dpfzxttukvsmluvp-o.jpg?1399222059');
	background-repeat: no-repeat;
	background-size: 100%;
	opacity: 0.9;
	filter: alpha(opacity = 40);
	height: 100%;
	width: 100%;
}

body {
	background-size: 100%;
	background-repeat: repeat-y;
	background-image: url('resources/images/adminhome.jpg');
	font-family: Papyrus, fantasy;
	font-weight: bold;
}

#title {
	width: 350px;
	height: 26px;
	padding-top: 15px;
	text-transform: uppercase;
	letter-spacing: 2px;
	text-align: center;
	font-size: 22px;
	margin-left: 90px;
	color: #ffffff;
}

.bodycontent {
	margin: auto;
	margin-top: 70px;
	width: 35%;
}

.mybutton {
	background-color: #DF6F12;
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

img {
	height: 100px;
	width: 100px;
}

input[type=checkbox] {
	zoom: 1.5;
}

.nav-tabs {
	background-color: #ffffff;
}

.tab-content {
	background-color: #ffffff;
	width: 740px;
	padding: 1px;
}

.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover
	{
	background-color: #ffffff;
}

td {
	min-width: 100px;
	text-align: center;
	padding: 7px;
}

tr {
	padding: 15px;
}

th {
	padding: 15px;
	text-align: center;
	background: #d15d10;
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
					<li><a href="/OnlineOrdering/home">Home</a></li>
					<li><a href="/OnlineOrdering/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!---navbar----->

	<div class="bodycontent">
		<br>
		<br>
		<div id="title" style="margin-left: 160px;">
			<b>Delete Queued Orders</b>
		</div>
		<br>
		<br>
		<br>
		<br>
		<div class="tab-content">
			<div id="Drinks" class="tab-pane fade in active">
				<table id="deletetable">

					<tr>
						<th></th>
						<th>Order Id</th>
						<th>Status</th>
						<th>Pickup Date</th>
						<th>Pickup Time</th>

						<th>Item Name</th>
						<th>Quantity</th>
					</tr>
					<c:forEach var="list1" items="${orderlist}" varStatus="status">
						<tr style="border: 2px solid #103F53;">
							<td><input id="cb1" type="checkbox" name="checker1" />
							<td>${list1.orderid}</td>
							<td>${list1.status}</td>
							<td>${list1.pickup_date}</td>
							<td>${list1.pickup_time}</td>
							<td colspan=2><c:forEach var="list" items="${list1.menumap}"
									varStatus="status">
									<table>
										<tr>
											<td>${list.key}</td>
											<td>${list.value}</td>
										</tr>
									</table>
								</c:forEach></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<br>
		<br>

		<button id="jqcc" class=mybutton>Delete Selected Items</button>

		<form action="deleteOrders" method="POST">
			<input type="hidden" id="itemData" name="itemData">
			<p>Please press confirm delete to make sure you want to delete
				these items</p>
			<br> <input type="submit" class=mybutton id="next"
				value="Confirm Delete">

		</form>

		<div id="demo1"></div>
	</div>
	<script>
		$(function() {
			var tableControl1 = document.getElementById('deletetable');
			var arrayOfDrinks = [];

			$('#jqcc')
					.click(
							function() {
								$('input:checkbox:checked', tableControl1)
										.each(
												function() {
													arrayOfDrinks.push($(this)
															.closest('tr')
															.find('td').eq(1)
															.text());
												}).get();

								$('input:checkbox').removeAttr('checked');
								var arrayOfItemNames = arrayOfDrinks;
								var dataToSend = arrayOfItemNames.join(";;");
								//document.getElementById("demo1").innerHTML = dataToSend;

								$('#next')
										.click(
												function() {
													document
															.getElementById("itemData").value = dataToSend;
												});
							});
		});
	</script>
</body>
</html>