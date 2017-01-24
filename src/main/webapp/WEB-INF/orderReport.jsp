<html lang="en">
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.2.min.js"></script>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="http://trentrichardson.com/examples/timepicker/jquery-ui-timepicker-addon.js"></script> 


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
  background-image:url('resources/images/reports.jpg');
 background-repeat: no-repeat;
    background-size: 100%;
    opacity: 0.9;
    filter:alpha(opacity=40);
    height:100%;
    width:100%;
 }


body {
  background-size: 100%;
    background-repeat: no-repeat;
	color: #000000;
	font-family: Papyrus, fantasy;
	font-weight: bold;

}

.col2 {
	width: 195px;
	height: 31px;
	display: block;
	float: left;
	margin: 0;
	
}

.mybutton{
background-color: #DF6F12;
color:black;
border: 1px solid #DF6F12;
border-radius: 4px;
padding: 5px 12px;
font-size: 16px;	
	width:90px;
	text-align:center;
}

.mybutton:hover {
    background-color: #031E2A;
    color: #7EB2C8;
}

.bodycontent {
	margin: auto;
	margin-top: 80px;
	width: 20%;
}

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



</style>


</head>
<body>
<div  id="backgroundImage">
<!---navbar----->
<div id="custom-bootstrap-menu" class="navbar navbar-default navbar-fixed-top" role="navigation" style="background:#df6f12; height:70px;">
    <div class="container-fluid" style="margin-top:15px; font-size:20px;margin-right:12px;">
        <div class="collapse navbar-collapse navbar-menubuilder" >
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/OnlineOrdering/AdminHome">Home</a>
                </li>
                <li><a href="/OnlineOrdering/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<!---navbar----->
<div style="margin-top:100px; margin-left:100px;">
		<form method="post" action="vieworders" >
		Start Date:
		<input type="text" id="startDate" name="startDate" /><br><br>
		End Date: 
		<input type="text" id="endDate" name="endDate" /><br><br>
				<label>Sort by:&nbsp;&nbsp;</label>
				<select id="sortby" name="sortby" style="width:165px;">
						<option value="ordertime">Order time</option>
						<option value="starttime">Fulfillment Start Time</option>
				</select>
<br><br><br>
		<button type="submit" class=mybutton>Generate</button>
		
		</form></div>
		<script>
    $(function () {
        $('#startDate').datepicker({
		   
		   dateFormat: 'yy-mm-dd'

		});
        
        $('#endDate').datepicker({
 		   
 		   dateFormat: 'yy-mm-dd'

 		});
        
      }); 
    
    
</script>  
</div>
</body>
</html>