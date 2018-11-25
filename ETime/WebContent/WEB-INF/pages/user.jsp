<%@page import="etime.bean.User"%>
<%@page import="etime.bean.ResourceRequest"%>
<%@page import="etime.bean.Resource"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/bootstrap-theme.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Page</title>
<script type="text/javascript">
function foo1(x) {
	document.getElementById("requestDetailsCollectorDiv").style.display = "block";
	document.getElementById("rId").value = x;
}
function foo2(x) {
	document.getElementById("rrId").value = x;
}
</script>
</head>
<body>
<%String status = (String)request.getAttribute("status");%>
<%User user = (User)request.getSession().getAttribute("user"); %>
<%String username = user.getUsername(); %>

<div>
	<form action="etimeController" id="mainForm">
	<div class="border pb-3">
		<div class="float-left">
			<h1>ETime</h1>
		<input type="submit" class="btn btn-primary" name="myAction" value="ResourceList">
		<input type="submit" class="btn btn-primary" name="myAction" value="History">
		</div>
		<div class="float-right">
			
		<h3>Hi <%=username %></h3>
		<input type="submit" class="btn btn-primary" name="myAction" value="logout">
		</div>
		<div class="clearfix"></div>
		</form>
	</div>

<div id="HistoryDiv">
	<form action="etimeController">
	<input type="hidden" name="rrId" id="rrId">
	<%List<ResourceRequest> resourceRequests = (List<ResourceRequest>)request.getAttribute("resourceRequests"); %>
	<%if(resourceRequests != null) { %>
		<table class="table table-bordered">
			<tr>
				<td>Request ID</td> 
				<td>Resource ID</td>
				<td>From</td> 
				<td>To</td> 
				<td>Status</td>
				<td></td> 			
			</tr>
		<%for(ResourceRequest r : resourceRequests) { %>
			<tr>
				<td><%=r.getResourceRequestId() %></td> 
				<td><%=r.getResourceId() %></td> 
				<td><%=r.getFrom() %></td> 
				<td><%=r.getTo() %></td> 
				<td><%=r.getStatus() %></td>
				<%if("cancelled".equals(r.getStatus())) {%>
					<td></td>
				<%} else { %> 
					<td><input type="submit" class="btn btn-primary" onclick="foo2(<%=r.getResourceRequestId() %>);"name="myAction" value="Cancel"></td>
				<%} %>
			</tr>
		<%} %>
		</table>
	<%} %> 
	</form>
</div>
<%if(resourceRequests != null && resourceRequests.size()==0) { %>
	<h3>No past requests</h3>
<%} %>

<div id="requestDetailsCollectorDiv" style="display: none;">
	<form action="etimeController">
		<input type="hidden" name="myAction" value="request">
		<input type="hidden" name="rId" id="rId">
		<table class="table table-bordered">
			<tr>
				<td>From</td> 
				<td><input type="datetime-local" name="from"></td> 
			</tr>
			<tr>
				<td>To</td> 
				<td><input type="datetime-local" name="to"><br></td> 
			</tr>
		</table>
		<input type="submit" value="Request"><br>
	</form>
</div>


<div id="ResourceListDiv" style="display: block;">
	<%HashMap<String,List<Resource>> resourceMap = (HashMap<String,List<Resource>>) request.getAttribute("resourceMap"); %>
	<%if(resourceMap != null) { %>
		<table class="table table-bordered">
		<%for(String key : resourceMap.keySet()) { %>
			<%for (Resource r : resourceMap.get(key)) {%>
			<tr>
				<td width=33%><%=r.getName()%></td>
				<td width=33%><%=r.getCapacity()%></td>
				<td width=33%><button onclick="foo1(<%=r.getId() %>);">Request</button><br></td>
			</tr>
			<%} %>
		<%} %>
		</table>
	<%} %>
</div>
<%if(resourceMap != null && resourceMap.size()==0) { %>
	<h3>No past requests</h3>
<%} %>
<%if(status != null) { %>
	<h3><%=status %></h3>
<%} %>
<script src="js/bootstrap.min.js"></script>
</body>
</html>