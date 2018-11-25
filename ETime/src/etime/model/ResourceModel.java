package etime.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sun.istack.internal.logging.Logger;

import etime.bean.Resource;
import etime.daoImpl.DaoImpl;

public class ResourceModel {
	Logger logger = Logger.getLogger(ResourceModel.class);
	Resource resource;
	String operationStatus;
	
	public void paramParser(HttpServletRequest request) {
		String name = request.getParameter("name");
		Integer capacity = Integer.parseInt(request.getParameter("capacity"));
		String type = request.getParameter("type");
		Integer superUserId = Integer.parseInt(request.getParameter("superUserId"));
		resource = new Resource(name, capacity, type, superUserId);
		System.out.println(resource);
	}
	
	public void saveResource(HttpServletRequest request)  {
		DaoImpl obj = new DaoImpl();
		int status = obj.saveResource(resource);
		if(status == 1) {
			request.setAttribute("status", "Resource Succesfully Added");
		} else {
			request.setAttribute("status", "Resource Addition Failed");
		}
	}
	
	public void getResourceMap(HttpServletRequest request) {
		DaoImpl obj = new DaoImpl();
		HashMap<String, List<Resource>> ls = new HashMap<String, List<Resource>>();
		List<Resource> resources = obj.getResourceMap();
		for(Resource r : resources) {
			String type = r.getType();
			if(ls.containsKey(type)) {
				ls.get(type).add(r);
			} else {
				ls.put(type, new ArrayList<Resource>());
				ls.get(type).add(r);
			}
		}
		request.setAttribute("resourceMap", ls);
	}



	public void updateResource(HttpServletRequest request) {
		String operation = request.getParameter("buttonType");
		Integer rId = Integer.parseInt(request.getParameter("rId"));
		int status;
		if("edit".equals(operation)) {
			String name = request.getParameter("name");
			Integer capacity = Integer.parseInt(request.getParameter("capacity"));
			String type = request.getParameter("type");
			Resource r = new Resource(rId, name, capacity, type);
			DaoImpl obj = new DaoImpl();
			status = obj.editRsource(r);
			if(status == 1) {
				request.setAttribute("status", "Resource Succesfully Edited");
			} else {
				request.setAttribute("status", "Resource Edit Failed");
			}
		} else {
			DaoImpl obj = new DaoImpl();
			obj.cancelRequests(rId);
			status = obj.deleteRsource(rId);
			if(status == 1) {
				request.setAttribute("status", "Resource Succesfully Deleted");
			} else {
				request.setAttribute("status", "Resource Deletion Failed");
			}
		}
	}

}
