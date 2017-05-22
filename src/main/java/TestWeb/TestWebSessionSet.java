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
			e.printStackTrace();
		}
    	
    	  resp.getWriter().print("设置完毕，设置数据的uuid为："+ThreadLocalUtil.get());
    	  //String url=SessionManager.encodeURL("/Session/get");
    	  //resp.sendRedirect(url);
      }	
    	
    	
       @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	super.doPost(req, resp);
    }
}
