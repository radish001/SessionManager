package TestWeb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestTomcatSessionSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getSession().setAttribute("123", "321");
    	String url=resp.encodeRedirectURL("/Session/TomcatGet");
    	//resp.getWriter().println("已经设置好,当前线程："+Thread.currentThread().getName());
        resp.sendRedirect(url);
    }
}
