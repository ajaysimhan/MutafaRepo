package etime.bean;

public class User {
	private int eid;
	private String username;
	private String password;
	private String role;
	
	public User() {}
	
	

	public User(int eid, String username, String password, String role) {
		super();
		this.eid = eid;
		this.username = username;
		this.password = password;
		this.role = role;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}



	@Override
	public String toString() {
		return "User [eid=" + eid + ", username=" + username + ", password="
				+ password + ", role=" + role + "]";
	}
	
}
