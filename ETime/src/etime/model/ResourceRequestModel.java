package etime.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.sun.istack.internal.logging.Logger;
import etime.bean.Resource;
import etime.bean.ResourceRequest;
import etime.bean.User;
import etime.daoImpl.DaoImpl;

public class ResourceRequestModel {
	Logger logger = Logger.getLogger(ResourceRequestModel.class);
	ResourceRequest resourceRequestNew;
	String operationStatus;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public void paramParser(HttpServletRequest request) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			Integer resourceId = Integer.parseInt(request.getParameter("rId"));
			String username = user.getUsername();
			Date from = getDate(request.getParameter("from"));
			Date to = getDate(request.getParameter("to"));
			resourceRequestNew = new ResourceRequest(resourceId, username, from, to);
		}  catch (Exception e) {
			System.out.println("Problem with parsing");
			System.exit(0);
			e.printStackTrace();
		}
	}

	private Date getDate(String parameter) {
		StringBuilder sb = new StringBuilder(parameter);
		Date d = null;
		sb.setCharAt(10, ' ');
		String str = sb.toString();
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			System.out.println("Problem with date conversion");
		}
		return d;
	}

	public String requestForResource(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		List<Resource> resources = obj.getResourceList();
		Resource r = null;
		for(Resource resource : resources) {
			if(resource.getId().equals(resourceRequestNew.getResourceId())) {
				r = resource;
				break;
			}
		}
		if(r.getSuperUserId() >0) {
			resourceRequestNew.setStatus("pending");
			int status = obj.placeRequest(resourceRequestNew);
			if(status == -1) {
				request.setAttribute("status", "Request was not succesful ! Try Again");
			} else {
				request.setAttribute("status", "Request approval is pending");
			}
		} else {
			List<ResourceRequest> resourceRequests = obj.getResourceRequestsList(r.getId());
			int flag = 1;
			Date fromNew = resourceRequestNew.getFrom();
			Date toNew = resourceRequestNew.getTo();

			for(ResourceRequest resourceRequestOld : resourceRequests) {
				Date fromOld = resourceRequestOld.getFrom();
				Date toOld = resourceRequestOld.getTo();
				boolean overlap = overlap(fromNew, toNew, fromOld, toOld);
				boolean statusGranted = "granted".equals(resourceRequestOld.getStatus());
				if(overlap && statusGranted) {
					flag = 0;
					break;
				} 
			}
			if(flag == 1) {
				resourceRequestNew.setStatus("granted");
				int status = obj.placeRequest(resourceRequestNew);
				if(status == -1) {
					request.setAttribute("status", "Request was not succesful ! Try Again");
				} else {
					request.setAttribute("status", "Request successful");
				}
			} else {
				resourceRequestNew.setStatus("cancelled");
				int status = obj.placeRequest(resourceRequestNew);
				if(status == -1) {
					request.setAttribute("status", "Request was not succesful ! Try Again");
				} else {
					request.setAttribute("status", "Someonoe else already booked it");
				}
			}
		}
		return null;
	}
	
	private boolean overlap(Date fromNew, Date toNew, Date fromOld, Date toOld) {
		boolean left =  fromNew.compareTo(fromOld) < 0 && toNew.compareTo(fromOld) < 0;
		boolean right = fromNew.compareTo(toOld) > 0 && toNew.compareTo(toOld) > 0;
		if(left || right) {
			return false;
		} else {
			return true;
		}
	}

	public void getResourceRequestsForUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		String username = user.getUsername();
		DaoImpl obj = new DaoImpl();
		List<ResourceRequest> resourceRequests;
		resourceRequests = obj.getResourceRequestsForUser(username);
		request.setAttribute("resourceRequests", resourceRequests);
	}

	public void getPendingRequests(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		User user = (User) request.getSession().getAttribute("user");
		Integer superUserId = user.getEid();
		List<Resource> resources = obj.getResourceList();
		List<ResourceRequest> resourceRequests;
		List<ResourceRequest> pendingResourceRequests = new ArrayList<ResourceRequest>();
		resourceRequests = obj.getResourceRequestsForPrivilagedUser();
		
		Resource tmp = null;
		for(ResourceRequest r : resourceRequests) {
			for(Resource resource : resources) {
				if(resource.getId().equals(r.getResourceId())) {
					tmp = resource;
					break;
				}
			}
			if("pending".equals(r.getStatus()) && tmp.getSuperUserId().equals(superUserId)) {
				pendingResourceRequests.add(r);
			}
		}
		request.setAttribute("pendingResourceRequests", pendingResourceRequests);
	}

	public void getGrantedRequests(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		User user = (User) request.getSession().getAttribute("user");
		Integer superUserId = user.getEid();
		List<Resource> resources = obj.getResourceList();
		List<ResourceRequest> resourceRequests;
		List<ResourceRequest> grantedResourceRequests = new ArrayList<ResourceRequest>();
		resourceRequests = obj.getResourceRequestsForPrivilagedUser();
		Resource tmp = null;
		for(ResourceRequest r : resourceRequests) {
			for(Resource resource : resources) {
				if(resource.getId().equals(r.getResourceId())) {
					tmp = resource;
					break;
				}
			}
			if("granted".equals(r.getStatus()) && superUserId.equals(tmp.getSuperUserId())) {
				grantedResourceRequests.add(r);
			}
		}
		request.setAttribute("grantedResourceRequests", grantedResourceRequests); ;
	}

	public String approveRequest(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		Integer rrId = Integer.parseInt(request.getParameter("rrId"));
		List<ResourceRequest> resourceRequests = obj.getResourceRequestsForPrivilagedUser();
		ResourceRequest rObj = null;
		for(ResourceRequest resourceRequest : resourceRequests) {
			if(rrId.equals(resourceRequest.getResourceRequestId())) {
				rObj = resourceRequest;
				break;
			}
		}
		
		int flag = 1;
		Date fromNew = rObj.getFrom();
		Date toNew = rObj.getTo();
		Integer rId = rObj.getResourceId();
		
		for(ResourceRequest resourceRequest : resourceRequests) {
			Date fromOld = resourceRequest.getFrom();
			Date toOld = resourceRequest.getTo();
			boolean sameResource = rId.equals(resourceRequest.getResourceId());
			boolean statusGranted = "granted".equals(resourceRequest.getStatus());
			boolean overlap = overlap(fromNew, toNew, fromOld, toOld);
			if(sameResource && statusGranted && overlap) {
				flag = 0;
				break;
			}	
		}
		if(flag == 1) {
			int status = obj.approveRequest(rrId);
			if(status == -1) {
				request.setAttribute("status", "Approval not successful");
			} else {
				request.setAttribute("status", "Approved");
			}
		} else {
			request.setAttribute("status", "Resource is already allocated");
		}
		return null;
	}

	public String cancelRequest(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		Integer rrId = Integer.parseInt(request.getParameter("rrId"));
		int status = obj.cancelRequest(rrId);
		if(status == -1) {
			request.setAttribute("status", "Cancellation not Successful");
		} else {
			request.setAttribute("status", "Cancellation Successful");
		}
		return null;
	}
}