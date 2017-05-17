package TestSessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TestRequest {
          public void set(HttpSession session,String key,Object value){
        	  session.setAttribute(key, value);
          }
          
          public Object get(HttpServletRequest request,String key){
        	  return  request.getSession().getAttribute(key);
          }
}
