package TestSessionManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Request;

public class TestSession extends HttpServlet {
      @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	
    	TestRequest test=new TestRequest();
    	//test.set(, key, value);
    	System.out.println("设置值");
    	req.getRequestDispatcher("/hello.jsp").forward(req, resp);
    }
}
