<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CMPE275_TermProject_Group5:Place Order</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
#backgroundImage{z-index: 1;}

#backgroundImage:before {
   content: "";
   position: absolute;
   z-index: -1;
   top: 0;
   bottom: 0;
   left: 0;
   right: 0;
  background-image:url('../resources/images/Seamless-Food-Delivery_stepOne_Burrito_13.jpg');
 background-repeat: no-repeat;
    background-size: 100%;
    opacity: 0.9;
    filter:alpha(opacity=40);
    height:100%;
    width:100%;
 }


body {
  background-size: 100%;
    background-repeat:repeat-y;
background-image:url('../resources/images/Seamless-Food-Delivery_stepOne_Burrito_13.jpg');
color:#000000;
	font-family: Papyrus, fantasy;
	font-weight: bold;

}

a{
color:black;
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
.button{
background-color: #DF6F12;
color:black;
border: 1px solid #031E2A;
border-radius: 4px;
padding: 5px 12px;
font-size: 14px;
float:left;
margin-right:300px;
}

.button:hover {
	background-color: #031E2A;
	color: #7EB2C8;
}

.bodycontent {
	margin: auto;
	margin-top: 50px;
	width: 30%;
}

.mybutton{
background-color: #DF6F12;
color:black;
border: 1px solid #031E2A;
border-radius: 4px;
padding: 5px 12px;
font-size: 14px;
margin-left:66%;

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

.nav-tabs{
  background-color:#ffffff;
  width:520px;
  color: #000000;
}
.tab-content{
    background-color:#ffffff;
	width:520px;
    padding:5px;
    color: #000000;
}
.nav-tabs {
	color:#ffffff;
	background:#d15d10;
	color: #000000;
}

.tab-content {
	background-color: #ffffff;
	padding: 5px;
	color: #000000;

}

li{
color: #000000;
}

.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover
	{
	background-color: #ffffff;
	color: #000000;
}

td {
	min-width: 70px;
	text-align: center;
	padding: 7px;
}

tr {
	padding: 15px;
}

th {
	padding: 15px;
	text-align: center;
}
</style>


</head>
<body id="backgroundImage">
<!---navbar----->
<div id="custom-bootstrap-menu" class="navbar navbar-default navbar-fixed-top" role="navigation" style="background:#df6f12; height:70px;">
    <div class="container-fluid" style="margin-top:15px;font-size:20px;">
        <div class="collapse navbar-collapse navbar-menubuilder" >
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/OnlineOrdering/home">Home</a>
                </li>
                <li><a href="/OnlineOrdering/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<!---navbar----->

<br><br><br><br><br><br>
	<div style="margin-left:400px; color:#ffffff;"><h2><strong>Hello ${user} !</strong></h2></div>
	<div class="bodycontent">

		<div id="title" style="color:white;">
			<b><strong>Select Your Order</strong></b>
		</div>
		<br>
		<br><br><br>
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#Drinks">Drinks</a></li>
			<li><a data-toggle="tab" href="#Appetizers">Appetizers</a></li>
			<li><a data-toggle="tab" href="#Maincourse">Main Course</a></li>
			<li><a data-toggle="tab" href="#Deserts">Deserts</a></li>

		</ul>
		<div class="tab-content">
			<br>
			<br>

			<div id="Drinks" class="tab-pane fade in active">
				<table id="drinktable">
					<tr>
						<th></th>
						<th></th>

                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Calories</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach var="list1" items="${list_drink}" varStatus="status">
                    <tr>
                        <td><input id="cb1" type="checkbox" name="checker1"
                            <c:if test="${not empty BulkList}">
                                <c:if test="${BulkList.containsKey(list1.name)}">checked="checked"</c:if>
                            </c:if><br/>
                        <td><img alt=""
                                 src="data:image/jpeg;base64,${list1.picpath}"></td>
                        <td>${list1.name}</td>
                        <td>${list1.unitprice}</td>
                        <td>${list1.calories}</td>
                        <c:set var="quan" value="1"/>
                        <c:if test="${not empty BulkList}">
                            <c:if test="${BulkList.containsKey(list1.name)}">
                                <c:set var="quan" value="${BulkList[list1.name]}"/>
                            </c:if>
                        </c:if>
                        <td><select class="1-100" id="quan_1">
                            <option selected="selected">${quan}</option>
                        </select></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

			<div id="Appetizers" class="tab-pane fade">
				<table id="appetizertable">
					<tr>
						<th></th>
						<th></th>

						<th>Item Name</th>
						<th>Price</th>
						<th>Calories</th>
						<th>Quantity</th>
					</tr>
					<c:forEach var="list2" items="${list_appetizer}" varStatus="status">
						<tr>
							<td><input id="cb1" type="checkbox" name="checker1"
								<c:if test="${not empty BulkList}"><c:if test="${BulkList.containsKey(list2.name)}">checked="checked" </c:if>
							</c:if> />
							<td><img alt=""
								src="data:image/jpeg;base64,${list2.picpath}"></td>
							<td>${list2.name}</td>
							<td>${list2.unitprice}</td>

                        <td>${list2.calories}</td>
                        <c:set var="quan" value="1"/>
                        <c:if test="${not empty BulkList}">
                            <c:if test="${BulkList.containsKey(list2.name)}">
                                <c:set var="quan" value="${BulkList[list2.name]}"/>
                            </c:if>
                        </c:if>
                        <td><select class="1-100" id="quan_2">
                            <option selected="selected">${quan}</option>
                        </select></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

			<div id="Maincourse" class="tab-pane fade">
				<table id="maincoursetable">
					<tr>
						<th></th>
						<th></th>

                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Calories</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach var="list3" items="${list_maincourse}"
                           varStatus="status">
                    <tr>
                        <td>
                            <input id="cb1" type="checkbox" name="checker1"
                            <c:if test="${not empty BulkList}">
                                <c:if test="${BulkList.containsKey(list3.name)}">checked="checked"</c:if>
                            </c:if>
                            />
                        <td><img alt=""
                                 src="data:image/jpeg;base64,${list3.picpath}"></td>
                        <td>${list3.name}</td>
                        <td>${list3.unitprice}</td>
                        <td>${list3.calories}</td>
                        <c:set var="quan" value="1"/>
                        <c:if test="${not empty BulkList}">
                            <c:if test="${BulkList.containsKey(list3.name)}">
                                <c:set var="quan" value="${BulkList[list3.name]}"/>
                            </c:if>
                        </c:if>
                        <td><select class="1-100" id="quan_3">
                            <option selected="selected">${quan}</option>
                        </select></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div id="Deserts" class="tab-pane fade">
            <table id="deserttable">
                <tr>
                    <th></th>
                    <th></th>

                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Calories</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach var="list4" items="${list_desert}" varStatus="status">
                    <tr>
                        <td>
                            <input id="cb1" type="checkbox" name="checker1"
                            <c:if test="${not empty BulkList}">
                                <c:if test="${BulkList.containsKey(list4.name)}">checked="checked"</c:if>
                            </c:if>
                            />
                        <td><img alt=""
                                 src="data:image/jpeg;base64,${list4.picpath}"></td>
                        <td>${list4.name}</td>
                        <td>${list4.unitprice}</td>

                        <td>${list4.calories}</td>
                        <c:set var="quan" value="1"/>
                        <c:if test="${not empty BulkList}">
                            <c:if test="${BulkList.containsKey(list4.name)}">
                                <c:set var="quan" value="${BulkList[list4.name]}"/>
                            </c:if>
                        </c:if>
                        <td><select class="1-100" id="quan_4">
                            <option selected="selected">${quan}</option>
                        </select></td>
                    </tr>
                </c:forEach>
            </table>
        </div>



	
	
  </div></div> 
		<br><br>
  <button class="mybutton" id="jqcc">Add to cart</button>
  <br>
  <div style="float:right;margin-right:180px;"><form action="Checkout" method="POST">
  	<input type="hidden" id="itemData" name="itemData"><br />
  	<input type="submit" class="button" id="next"
				value="Proceed to checkout" />
  </form></div><br><br><br>
	 
  <div id="demo1"></div>

<script>
	$(function() {
		var $select = $(".1-100");
		for (i = 1; i <= 100; i++) {
			$select.append($('<option></option>').val(i).html(i));
		}
		var tableControl1 = document.getElementById('drinktable');
		var tableControl2 = document.getElementById('appetizertable');
		var tableControl3 = document.getElementById('maincoursetable');
		var tableControl4 = document.getElementById('deserttable');
		var arrayOfDrinks = [];
		var arrayOfAppetizers = [];
		var arrayOfMaincourse = [];
		var arrayOfDeserts = [];
		$('#jqcc')
				.click(
						function() {
							$('input:checkbox:checked', tableControl1)
									.each(
											function() {
												arrayOfDrinks
														.push($(this).closest(
																'tr')
																.find('td').eq(
																		2)
																.text()
																+ '::'
																+ $(this)
																		.closest(
																				'tr')
																		.find(
																				'td select#quan_1 option:selected')
																		.text());
											}).get();

							$('input:checkbox:checked', tableControl2)
									.each(
											function() {
												arrayOfAppetizers
														.push($(this).closest(
																'tr')
																.find('td').eq(
																		2)
																.text()
																+ '::'
																+ $(this)
																		.closest(
																				'tr')
																		.find(
																				'td select#quan_2 option:selected')
																		.text());
											}).get();

							$('input:checkbox:checked', tableControl3)
									.each(
											function() {
												arrayOfMaincourse
														.push($(this).closest(
																'tr')
																.find('td').eq(
																		2)
																.text()
																+ '::'
																+ $(this)
																		.closest(
																				'tr')
																		.find(
																				'td select#quan_3 option:selected')
																		.text());
											}).get();

							$('input:checkbox:checked', tableControl4)
									.each(
											function() {
												arrayOfDeserts
														.push($(this).closest(
																'tr')
																.find('td').eq(
																		2)
																.text()
																+ '::'
																+ $(this)
																		.closest(
																				'tr')
																		.find(
																				'td select#quan_4 option:selected')
																		.text());
											}).get();

							$('input:checkbox').removeAttr('checked');
							var arrayOfItemNames = arrayOfDrinks.concat(
									arrayOfAppetizers, arrayOfMaincourse,
									arrayOfDeserts);
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
