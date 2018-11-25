package etime.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.istack.internal.logging.Logger;

import etime.bean.Resource;
import etime.bean.ResourceRequest;
import etime.bean.User;
import etime.dao.Dao;
import etime.daoUtil.MySQLConnection;


public class DaoImpl implements Dao{
	static Logger logger = Logger.getLogger(DaoImpl.class);

	public List<User> getUsers() {
		try {
			List<User> users = new ArrayList<User>();
			String sql = "select * from user";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				users.add(new User(rs.getInt(1),rs.getString(2),
						rs.getString(3),rs.getString(4)));
			}
			stmt.close();
			rs.close();
			conn.close();
			return users;
		} catch (Exception e) {
			System.out.println("Problem with getting users");
			e.printStackTrace();
			return null;
		}
	}

	public int saveResource(Resource resource) {
		try {
			String sql = "insert into resource values(?,?,?,?,?)";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select max(id)  from resource");
			rs.next();
			Integer id = rs.getInt(1);
			if(id == 0) {
				id = 201;
			} else {
				id++;
			}
			rs.close();
			stmt.setInt(1,id);
			stmt.setString(2, resource.getName());
			stmt.setInt(3, resource.getCapacity());
			stmt.setString(4, resource.getType());
			stmt.setInt(5, resource.getSuperUserId());
			
			if(stmt.executeUpdate() == 1) {
				stmt.close();
				conn.close();
				return 1;
			} else {
				stmt.close();
				conn.close();
				return -1;
			}
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public List<Resource> getResourceList() {
		List<Resource> resources = new ArrayList<Resource>();
		try {
			String sql = "select * from Resource";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				resources.add(new Resource(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5)));
			}
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Problem with fetching resources");
			e.printStackTrace();
		}
		return resources;
	}


	public List<Resource> getResourceMap() {
		try {
			String sql = "select * from resource";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Resource> resources = new ArrayList<Resource>();
			while(rs.next()) {
				resources.add(new Resource(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
			}
			stmt.close();
			conn.close();
			return resources;
		} catch (SQLException e) {
			System.out.println("Problem");
			e.printStackTrace();
			return null;
		}
		
	}

	public int editRsource(Resource r) {
		try {
			String sql = "update resource set name=?,capacity=?,type=? where id=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, r.getName());
			ps.setInt(2, r.getCapacity());
			ps.setString(3, r.getType());
			ps.setInt(4, r.getId());
			if(ps.executeUpdate() == 1) {
				ps.close();
				conn.close();
				return 1;
			} else {
				return -1;
			}
			
		} catch (SQLException e) {
			return -1;
		}
	}

	public int deleteRsource(Integer rId) {
		try {
			String sql = "delete from resource where id=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, rId);
			if(ps.executeUpdate() == 1) {
				System.out.println("Delete complete");
				ps.close();
				conn.close();
				return 1;
			} else {
				return -1;
			}
			
		} catch (Exception e) {
			System.out.println("Problem with delete");
			return -1;
		}
		
	}

	public int placeRequest(ResourceRequest resourceRequest) {
		try {
			String sql = "insert into resourceRequest values(?,?,?,?,?,?)";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select max(resourceRequestId)  from resourceRequest");
			rs.next();
			Integer id = rs.getInt(1);
			if(id == 0) {
				id = 1001;
			} else {
				id++;
			}
			rs.close();
			st.close();
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, resourceRequest.getResourceId());
			stmt.setString(3, resourceRequest.getUsername());
			stmt.setTimestamp(4, new Timestamp(resourceRequest.getFrom().getTime()));
			stmt.setTimestamp(5, new Timestamp(resourceRequest.getTo().getTime()));
			stmt.setString(6, resourceRequest.getStatus());
			
			if(stmt.executeUpdate() == 1) {
				stmt.close();
				conn.close();
				return 1;
			} else {
				stmt.close();
				conn.close();
				return 0;
			}
		} catch (SQLException e) {
			System.out.println("Problem with sql insertion");
			e.printStackTrace();
			return -1;
		}
	}

	public List<ResourceRequest> getResourceRequestsList(Integer id) {
		try {
			List<ResourceRequest> rr = new ArrayList<ResourceRequest>();
			String sql = "select * from resourceRequest where resourceId=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ResourceRequest obj = new ResourceRequest(rs.getInt(1), rs.getInt(2), 
						rs.getString(3), new Date(rs.getTimestamp(4).getTime()), 
						new Date(rs.getTimestamp(5).getTime()), rs.getString(6));
				rr.add(obj);
			}
			ps.close();
			conn.close();
			return rr;
		} catch (SQLException e) {
			System.out.println("Problem with fetching resource requests");
			System.exit(0);
			return null;
		}
	}

	public List<ResourceRequest> getResourceRequestsForUser(String username) {
		try {
			List<ResourceRequest> rr = new ArrayList<ResourceRequest>();
			String sql = "select * from resourceRequest where username=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ResourceRequest obj = new ResourceRequest(rs.getInt(1), rs.getInt(2), 
						rs.getString(3), new Date(rs.getTimestamp(4).getTime()), 
						new Date(rs.getTimestamp(5).getTime()), rs.getString(6));
				rr.add(obj);
			}
			ps.close();
			conn.close();
			return rr;
		} catch (SQLException e) {
			System.out.println("Problem with fetching resource requests");
			System.exit(0);
			return null;
		}
	}

	public List<ResourceRequest> getResourceRequestsForPrivilagedUser() {
		try {
			List<ResourceRequest> rr = new ArrayList<ResourceRequest>();
			String sql = "select * from resourceRequest";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ResourceRequest obj = new ResourceRequest(rs.getInt(1), rs.getInt(2), 
						rs.getString(3), new Date(rs.getTimestamp(4).getTime()), 
						new Date(rs.getTimestamp(5).getTime()), rs.getString(6));
				rr.add(obj);
			}
			ps.close();
			conn.close();
			return rr;
		} catch (SQLException e) {
			System.out.println("Problem with fetching pending resource requests");
			System.exit(0);
			return null;
		}
	}

	public int approveRequest(Integer rrId) {
		try {
			String sql = "update resourceRequest set status=? where resourceRequestId=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "granted");
			ps.setInt(2, rrId);
			if(ps.executeUpdate() == 1) {
				System.out.println("Update complete");
			}
			ps.close();
			conn.close();
			return 1;
		} catch (SQLException e) {
			System.out.println("Some Problem with update");
			e.printStackTrace();
			return -1;
		}
	}

	public int deleteRequests(Integer rId) {
		try {
			String sql = "delete from resourceRequest where resourceId=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, rId);
			if(ps.executeUpdate() == 1) {
				System.out.println("Delete complete");
			}
			ps.close();
			conn.close();
			return 1;
		} catch (SQLException e) {
			System.out.println("Some Problem with delete");
			e.printStackTrace();
			return -1;
		}
	}

	public int cancelRequest(Integer rrId) {
		try {
			String sql = "update resourceRequest set status=? where resourceRequestId=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "cancelled");
			ps.setInt(2, rrId);
			if(ps.executeUpdate() == 1) {
				System.out.println("Cancel complete");
			}
			ps.close();
			conn.close();
			return 1;
		} catch (SQLException e) {
			System.out.println("Some Problem with cancellation");
			e.printStackTrace();
			return -1;
		}
	}

	public int cancelRequests(Integer rId) {
		try {
			String sql = "update resourceRequest set status=? where resourceId=?";
			Connection conn = MySQLConnection.getMySQLConnection().connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "cancelled");
			ps.setInt(2, rId);
			if(ps.executeUpdate() == 1) {
				System.out.println("Cancel complete");
			}
			ps.close();
			conn.close();
			return 1;
		} catch (SQLException e) {
			System.out.println("Some Problem with cancellation");
			e.printStackTrace();
			return -1;
		}
	}
	
}
