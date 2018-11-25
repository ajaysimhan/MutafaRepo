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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/bootstrap-theme.css" rel="stylesheet" />
<title>Admin Page</title>
<script type="text/javascript">
	function foo1() {
		document.getElementById("resourceDetailsCollectorDiv").style.display = "block";
		document.getElementById("HistoryDiv").style.display = "none";
		document.getElementById("resourceListDiv").style.display = "none";
	}
	function foo2(rrId) {
		document.getElementById("rrId").value = rrId;
	}
	function editFunc(id) {
		document.getElementById("rId").value = id;
		document.getElementById("buttonType").value = "edit";
		document.getElementById("editDiv").style.display = "block";	
		
	}
	function deleteFunc(id) {
		document.getElementById("rId").value = id;
		document.getElementById("buttonType").value = "delete";
	}
	function requestFunc(id) {
		document.getElementById("requestDetailsCollectorDiv").style.display = "block";
		document.getElementById("rId1").value = id;
	}
</script>
</head>
<body >
<%String status = (String)request.getAttribute("status");%>
<%User user = (User)request.getSession().getAttribute("user"); %>
<%String username = "Ajay"; %>
	<form action="etimeController" id="mainForm">
	<div>
	<div class="border pb-3">
		<div class="float-left">
			<h1>ETime</h1>
			 <input type="button" class="btn btn-primary" id="addButton" value="AddResource" onclick="foo1();">
      <input type="submit" class="btn btn-primary" name="myAction" value="ResourceList">
      <input type="submit" class="btn btn-primary" name="myAction" value="History">
		</div>
		<div class="float-right">
			
		<h3>Hi <%=username %></h3>
      <input type="submit" class="btn btn-primary" name="myAction" value="logout">
		</div>
		<div class="clearfix"></div>
	</div>
  </form>

<div id="requestDetailsCollectorDiv" style="display: none;" style="margin:0px;">
	<form action="etimeController">
		<input type="hidden" name="myAction" value="request">
		<input type="hidden" name="rId" id="rId1">
		
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

<div id="resourceListDiv" style="display: block;margin:0px;">
	<%HashMap<String,List<Resource>> resourceMap = (HashMap<String,List<Resource>>) request.getAttribute("resourceMap"); %>
	<%if(resourceMap != null) { %>
		<form id="formId1" action="etimeController">
			<input type="hidden" name="myAction" value=updateResource>
			<div id="editDiv" style="display: none;">
				<table class="table table-bordered">
				<tr>
				<td>Name</td>
				<td><input type="text" name="name"></td>
				</tr>
				
				<tr>
				<td>Type</td>
				<td><input type="text" name="type"></td>
				</tr>
				
				<tr>
				<td>Capacity</td>
				<td><input type="text" name="capacity"></td>
				</tr>
				
				<tr>
				<td></td>
				<td><input type="submit" value="Save"></td>
				</table>
			</div>
			<input type="hidden" name="buttonType" id="buttonType">
			<input type="hidden" name="rId" id="rId">
			<table class="table">
				<tr>
					<td width=30%;>Resource Name</td>
					<td width=30%;>Capacity</td>
					<td width=20%;></td>
					<td width=20%;></td>
					<td width=20%;></td>
				</tr>
			<%for(String key : resourceMap.keySet()) { %>
				<%for(Resource r : resourceMap.get(key)) { %>
					<tr>
						<td width=30%;><%=r.getName() %></td>
						<td width=30%;><%=r.getCapacity() %></td>
						<td width=20%;><input type="button" name="edit" value="Edit" onclick="editFunc(<%=r.getId() %>);"></td>
						<td width=20%;><button name="delete" value="delete" onclick="deleteFunc(<%=r.getId() %>);">Delete</button></td>
						<td width=20%;><input type="button" value="Request" onclick="requestFunc(<%=r.getId() %>);"></td>
					</tr>
				<%} %>
			<%} %>
			</table>
			
		</form>
	<%} %>
</div>
<%if(resourceMap != null && resourceMap.size()==0) { %>
	<h3>No past requests</h3>
<%} %>
	
<div id="resourceDetailsCollectorDiv" style="display: none; margin:0px;margin:0px;">
	<form action="etimeController">
		<input type="hidden" name="myAction" value="readResourceDetails">
		<table class="table table-bordered">
			<tr>
				<td>Name</td> 
				<td><input type="text" name="name"></td> 
			</tr>
			
			<tr>
				<td>Type</td> 
				<td><input type="text" name="type"></td> 
			</tr>
			
			<tr>
				<td>Capacity</td> 
				<td><input type="text" name="capacity"></td> 
			</tr>
			
			<tr>
				<td>PrivilagedUserId</td> 		
				<td><input type="text" name="superUserId"></td> 
			</tr>
			
			<tr>
				<td></td> 
				<td><input type="submit" value="Add"></td> 
			</tr>
			
		</table>
		
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

<%if(status != null) { %>
	<h3><%=status %></h3>
<%} %>


<script src="js/bootstrap.min.js"></script>	
</body>
</html> 