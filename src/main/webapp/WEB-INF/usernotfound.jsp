<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cmpe275_Term_Project_5 : Error</title>
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
  background-image:url('resources/images/background-food-05.jpg');
 background-repeat: no-repeat;
    background-size: 100%;
    opacity: 0.4;
    filter:alpha(opacity=40);
    height:100%;
    width:100%;
 }
 
#myBackground{
 background-color: rgba(255,255,255, 0.5);
 border-radius:4px;
  color: inherit;
  width:120%;
  height:115%;
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
	font-size:12px;
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
	height:30px;
}

.textarea {
	border: 1px solid #8595B2;
	margin: 4px 0 5px 8px;
}

.button:hover {
    background-color: #031E2A;
    color: #7EB2C8;
} 
.mybutton
{
   margin-left : 140px;
}

.button{

background-color: #DF6F12;
color:black;
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
.exist{
margin-left : 155px;
}


.mybutton{
background-color: #DF6F12;
color:black;
border: 1px solid #DF6F12;
border-radius: 4px;
padding: 5px 12px;
font-size: 16px;
width:85px;
}
.mybutton:hover {
    background-color: #031E2A;
    color: #7EB2C8;
}

</style>
</head>
<body >
<body id="backgroundImage">
<div style="margin-left:400px;">
<br><br>
<h3>Error</h3>
<h3>${msg}</h3></div><br>
<div style="margin-left:350px;">
<input type="button" class=mybutton id="register" name="register" value="Register" onclick="location.href = 'register';"> 
<input type="button" class=mybutton id="login" name="login" value="Login" style="margin-left:30px;" onclick="location.href = 'home';"> 
</div>
</body>
</html>