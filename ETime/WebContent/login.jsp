<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/bootstrap-theme.css" rel="stylesheet" />
<title>Insert title here</title>

<script type="text/javascript">
	function validate() {
		var username = document.getElementById("idTextBox");
		var password = document.getElementById("idPassword");
		if(username.value.length == 0) {
			return false;
		}
		if(password.value.length == 0) {
			return false;
		}
	}
</script>

</head>

<body>

<%String status=request.getParameter("status");
if(status != null && status.length() != 0) { %>
	Login Failed
<%} %>
	
	
		<form action="etimeController" method="post">
			<input type="hidden" name="myAction" value="login">
			<table>
				<tr>
					<td>User Name :</td>
					<td><input type="text" id="idTextBox" name="username" /></td>
				</tr>
				<tr>
					<td>Password : </td>
					<td><input type="password" name="password" id="idPassword" name="password" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="login" value="login" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>