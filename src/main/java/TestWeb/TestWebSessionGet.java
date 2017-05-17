package TestWeb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pojo.User;
import sessionmanager.SessionManager;
import sessionmanager.ThreadLocalUtil;


public class TestWebSessionGet extends HttpServlet{
         @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        	 
        	 User value=null;
        	 try {
				value=(User)SessionManager.get("haha", User.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	 resp.getWriter().println("得到的值为："+value.toString()+"   sessionId为："+ThreadLocalUtil.get());
        }  
}
