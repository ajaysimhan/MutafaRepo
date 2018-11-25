package etime.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import etime.bean.User;
import etime.model.ResourceRequestModel;
import etime.model.ResourceModel;
import etime.model.UserModel;

public class EtimeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myAction = request.getParameter("myAction");
		User user = (User) request.getSession().getAttribute("user");
		
		ResourceModel rm = new ResourceModel();
		ResourceRequestModel rrm = new ResourceRequestModel();
		String role = UserModel.getRole(request);
		
		switch(myAction) 
		{	
		case "approve":
			rrm = new ResourceRequestModel();
			rrm.approveRequest(request);
			request.getRequestDispatcher("/WEB-INF/pages/"+role+".jsp").forward(request, response);
			break;
			
		case "Cancel":
			rrm = new ResourceRequestModel();
			rrm.cancelRequest(request);
			request.getRequestDispatcher("/WEB-INF/pages/"+role+".jsp").forward(request, response);
			break;
			
		case "logout":
			request.getSession().invalidate();
			response.sendRedirect("http://localhost:8080/ETime/login.jsp");
			break;
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String myAction = req.getParameter("myAction");
		if("login".equals(myAction)) {
			User user = null;
			if(user != null) {
				resp.sendRedirect("http://localhost:8080/ETime/login.jsp?status=yes");
			} else {
				createSession(req, user);
				req.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(req, resp);
			}
		}
	}
	
	private void createSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
