package sessionmanager;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class SessionIdFilter implements Filter{
	private static final Logger LOG=Logger.getLogger(SessionIdFilter.class);

	@Override
	public void destroy() {
		
		
	}

	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		if("request".equals(Config.getMode())){                      //如果是request模式，就初始化
			  HttpSession httpSession=HttpSession.getInstance();     //初始化HttpSession
			  Thread thread=new Thread(new DestoryHttpSessionThread());
			  thread.start();                                     //启动定时销毁HttpSession的线程
			  LOG.info("HttpSession已初始化，定时销毁线程已启动！");                                    
		      System.out.println("HttpSession已初始化，定时销毁线程已启动！");
		}
		  
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		    /**
		     * 获取sessionId 保证ThreadLocal中有sessionId
		     * 1、先去cookie中去取，能取到就设置到threadlocal，同时初始化session
		     * 2、如果取不到，再去ThrealLocal中取，能取到就设置到threadlocal，同时初始化session（这一步是上一个过滤器设置的）
		     * 3、cookie和ThrealLocal中都取不到，说明第一次访问并且没有禁用cookie，就创建sessionId，设置到threadlocal并初始化session
		     */
		    String sessionId=CookieUtils.getCookieValue((HttpServletRequest)request, Config.getCookieName());
		    if(sessionId!=null&&!sessionId.equals("")){
		      try {
		    		ThreadLocalUtil.set(sessionId);      //如果有此sessionID就放入到threadLocal中	  
					SessionManager.set("", "");          //这里是为了防止cookie中有此sessionId但是服务器session中没有的bug，同时也起到刷新时间的作用
		    		//SessionManager.refreshValid();      //浏览器每次和服务器有交互就刷新服务器中的session有效时间		   
			} catch (Exception e) {
				e.printStackTrace();
			}
			   CookieUtils.setMaxAge((HttpServletRequest)request, Config.getCookieName(), 0); //删除此cookie 再新建一样的cookie，等于刷新cookie时间
		       CookieUtils.setCookie((HttpServletRequest)request, (HttpServletResponse)response, Config.getCookieName(), sessionId, Integer.parseInt(Config.getSessionValid())*60);
		    }else{
		    	String localSessionId=ThreadLocalUtil.get();
		    	 if(localSessionId!=null&&!localSessionId.equals("")){
		    		 try {
						SessionManager.set("", "");
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	 }else{
		    		    String newSessionId=null;
		    		 try {
		 				newSessionId = SessionManager.set("", "");     //如果没有就在服务器创建session，生成uuid并放入ThreadLocal和cookie中
		 			} catch (Exception e) {
		 				e.printStackTrace();
		 			} 
		 			  if(newSessionId!=null){
		 				  ThreadLocalUtil.set(newSessionId);
		 			      CookieUtils.setCookie((HttpServletRequest)request, (HttpServletResponse)response, Config.getCookieName(), newSessionId, Integer.parseInt(Config.getSessionValid())*60);
		 			  }  
		    	 }
		              
			}                        
		    
		   chain.doFilter(request, response);
		   System.out.println("过滤完毕,sessionID为："+ThreadLocalUtil.get());
	}

}
