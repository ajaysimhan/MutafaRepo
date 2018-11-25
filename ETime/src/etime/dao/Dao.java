package etime.dao;

import java.util.List;
import etime.bean.Resource;
import etime.bean.ResourceRequest;
import etime.bean.User;

public interface Dao {
	List<User> getUsers();
	int saveResource(Resource resource);
	List<Resource> getResourceList();
	List<Resource> getResourceMap();
	int editRsource(Resource r);
	int deleteRsource(Integer rId);
	int placeRequest(ResourceRequest resourceRequest);
	List<ResourceRequest> getResourceRequestsList(Integer id);
	List<ResourceRequest> getResourceRequestsForUser(String username);
	List<ResourceRequest> getResourceRequestsForPrivilagedUser();
	int approveRequest(Integer rrId);
	int deleteRequests(Integer rId);
	int cancelRequest(Integer rrId);
	int cancelRequests(Integer rId);	
}
