package TestSessionManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestCookie extends HttpServlet{
  @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    Cookie cookie=new Cookie("testsession", "testnihao");
	    resp.addCookie(cookie);
	    req.getRequestDispatcher("/cookie").forward(req, resp);
	    
}
}
