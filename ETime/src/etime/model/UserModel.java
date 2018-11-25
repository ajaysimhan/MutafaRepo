package etime.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import etime.bean.User;
import etime.daoImpl.DaoImpl;

public class UserModel {

	static List<User> users = new ArrayList<User>();
	public static void populateUsers() {
		DaoImpl obj = new DaoImpl();
		users = obj.getUsers();
	}
	public static User validateUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		for(User user : users) {
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	public static String getRole(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			return user.getRole();
		}
		return null;
	}
	



}
