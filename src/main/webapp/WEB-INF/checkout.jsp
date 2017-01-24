<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<html>
<head>
<link
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen"
	href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">
<style>
 input[type=radio] {
    border: 0px;
    width:6%;
    height: 2em;
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

#backgroundImage{z-index: 1;}

#backgroundImage:before {
   content: "";
   position: absolute;
   z-index: -1;
   top: 0;
   bottom: 0;
   left: 0;
   right: 0;

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
	margin-left:90px;
	color:#ffffff;
}

form {
	width: 335px;
}

.col1 {
	text-align: left;
	width: 350px;
	height: 31px;
	margin: 0;
	float: left;
	margin-right: 2px;
font-size:22px;
	letter-spacing: 1px;
	
}

.col2 {
	width: 195px;
	height: 40px;
	display: block;
	float: left;
	margin: 0;
	
}

div.row1 {
	clear: both;
	width: 675px;
}


.bodycontent {
	margin: auto;
	margin-top: 50px;
	width: 30%;
	color:#ffffff;
}


.mybutton{
background-color: #DF6F12;
color:black;
border: 1px solid #031E2A;
border-radius: 4px;
padding: 5px 12px;
font-size: 14px;
}
.mybutton:hover {
    background-color: #031E2A;
    color: #7EB2C8;
}

input[type=checkbox] {
    zoom: 1.5;
}

label{
font-size:16px;
font-weight:bold;
}

tr{
pading-right:15px;
padding-bottom:10px;
}

</style>


</head>
<body id="backgroundImage">
<div class="container-fluid" style="margin:0; padding:0;">
<div class="row" style="height:50px;"><div class="span12"></div></div>
<div class="row" style="height:50px;"><div class="span12"></div></div>
	<div class="row"><div class="span3" style="width:30%;"></div>
		<div class="span6">
			<label class="col1" style="color:white;">
				<b>Order Id: </b>
				<span id="id" style="font-size:22px;color:white;">${orderid}</span>
			</label>
			<br>
			<table style="font-size:22px;color:white;">
				<tr><th style="text-align:left;color:white;">Item Name</th>
					<th style="text-align:left;color:white;">Quantity</th>
				</tr>
				<c:forEach var="list" items="${menulist}" varStatus="status">
					<tr style="color:white;">
						<td style="min-width:350px;height:35px;">${list.key}</td>
						<td style="min-width:350px;height:35px;text-align:left;">${list.value}</td>
					</tr><br>
				</c:forEach>
			</table>
		</div><div class="span3"></div>
	</div>
	<div class="row" style="height:50px;"><div class="span12"></div></div>
	<div class="row"><div class="span3" style="width:30%;"></div>
		<div class="span6">
			<label class="col1" style="color:white;" >Total Price:&nbsp;&nbsp;</label>
			<div class="col2" style="color:white; font-size:22px;" >$ ${totalprice}</div>
		
			<label class="col1">
				<input type="radio" onclick="confirm()"name="pickup" value="confirm"/>
				&nbsp;&nbsp;<span style="font-size:22px;color:white;">Earliest Pickup Time:</span> &nbsp;&nbsp;
			</label>
			<div class="col2" id="early" style="font-size:20px;color:white;">${earliestpickuptime}</div>
		
			<label class="col1">
			<input type="radio" onclick="custom()" name="pickup" value="custom" />
			&nbsp;&nbsp;<span style="color:white;">Pick your own date & time.(Between 6am-9pm, within next 30 days.)</span>&nbsp;&nbsp;
			</label><br><br><br><br>
			<div id="datetimepicker" class="input-append date">
				<input type="text" id="in"></input> <span class="add-on" id="in1"> <i
					data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
				</span>
			</div>
		
			<form method="post" action="finalCheckout">
				<input type="hidden" id="orderid" name="orderid" value="" />
				<input	type="hidden" id="type" name="type" value="" /> 
				<input type="hidden" id="time" name="time" value="" /> 
				<input type="hidden" id="earlypickuptime" name="early" value="" /><br><br><br>
				<input type="submit" name="proceed" class=mybutton value="Proceed" style="margin-left:0px;"/>
			</form>
		</div><div class="span3"></div>
	</div>
</div>

<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min.js">
</script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js">
</script>
<script type="text/javascript"
	src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
</script>
<script type="text/javascript"
	src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js">
</script>
<script type="text/javascript">

var today = new Date();
var dd = today.getDate();
var mm = today.getMonth(); 
var yyyy = today.getFullYear();
var future = new Date();
future.setDate(future.getDate() + 30);
var ddf = future.getDate();
var mmf = future.getMonth(); 
var yyyyf = future.getFullYear();

    $('#datetimepicker').datetimepicker({
        format: 'yyyy-MM-dd hh:mm',
        language: 'en',
        startDate: new Date(yyyy, mm, dd),
        endDate: new Date(yyyyf, mmf, ddf),
        pickSeconds: false
    });
    var picker = $('#datetimepicker');
    picker.on('changeDate', function(e) {
    	 var time = document.getElementById("in").value;
         var spl = time.split(" ");
         var hour = spl[1].split(":");
         var date = spl[0].split("-");
         
         var now = new Date(); 
		 var setDate = e.date;
         
         if (parseInt(hour[0]) < 6 || parseInt(hour[0]) > 21 )  {
             alert("Please select a valid time between 06:00 and 21:00 ");
             document.getElementById("in").value = "";
         } else if(parseInt(hour[0]) == 21) {
        	 if(parseInt(hour[1]) > 0) {
        		 alert("Please select a valid time between 06:00 and 21:00 ");
                 document.getElementById("in").value = "";
        	 }
         } /*else if (now.getTime() > setDate.getTime()) {
			 alert("Selected time is in past. Try Again!");
             document.getElementById("in").value = "";
         }*/
         
        document.getElementById("time").value = document.getElementById("in").value;
    });
     
    document.getElementById("orderid").value = document.getElementById ('id').innerHTML;
    document.getElementById("earlypickuptime").value = document.getElementById ('early').innerHTML;
    function confirm() {
        document.getElementById("type").value = "confirm";
        document.getElementById("in").disabled=true;
        document.getElementById("in1").style.visibility = "hidden";
    }
    function custom() {
	   document.getElementById("type").value = "custom";
	   document.getElementById("in").disabled=false;
       document.getElementById("in1").style.visibility = "visible";
    }
 
</script>

</body>
</html>