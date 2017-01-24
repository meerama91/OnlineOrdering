<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cmpe275_Term_Project_5 : Search the Item</title>

</head>
<body>
	<form action="searchItem" method="post">
		<table style="border:1px solid black;">
		<thead>
    <tr>
      <th style="border:1px solid black;">Image</th>
      <th style="border:1px solid black;">Name</th>
      <th style="border:1px solid black;">Unit Price</th>
      <th style="border:1px solid black;">Calories</th>
      <th style="border:1px solid black;">Select</th>
          </tr></thead>
			<c:forEach var="listValue" items="${itemlist}" varStatus="status">
				<tr style="border:1px solid black;" ><td style="border:1px solid black;">
				<!-- input type="checkbox" name="menulist" value=${listValue.name}--></td> 
				<td style="border:1px solid black;">${listValue.picture}</td>
				<td style="border:1px solid black;">${listValue.name}</td>
				<td style="border:1px solid black;">${listValue.unitprice}</td>
				<td style="border:1px solid black;">${listValue.calories}</td>
				</tr>
			</c:forEach>
		</table>
		
				<input type="submit" value="Delete" /></td>
			
	</form>
	<input type="button" class=button value="back" id="back" name="back"
		onclick="location.href = 'home'">
</body>
</html>