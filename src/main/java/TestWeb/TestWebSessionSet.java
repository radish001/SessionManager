package TestWeb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pojo.User;
import sessionmanager.DestoryHttpSessionThread;
import sessionmanager.SessionManager;
import sessionmanager.ThreadLocalUtil;


public class TestWebSessionSet extends HttpServlet{
       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//super.doGet(req, resp);
    	try {
    		User user=new User();
    		user.setUserName("Radish");
			SessionManager.set("haha", user);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
    	
    	
    	resp.getWriter().println("已经设置好，sessionId为："+ThreadLocalUtil.get());
    	
    }
       @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	super.doPost(req, resp);
    }
}
