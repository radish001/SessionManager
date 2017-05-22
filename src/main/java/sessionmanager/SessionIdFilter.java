package sessionmanager;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/**
 * 过滤器类
 * 拦截cookie取得sessionI的放入ThreadLocal，初始化Session，保证Cookie、ThreadLocal、session中的sessionId一致
 * @author hxd
 * @date 2017.5.16
 */
public class SessionIdFilter implements Filter{
	private static final Logger LOG=Logger.getLogger(SessionIdFilter.class);

	@Override
	public void destroy() {
		
		
	}

	
   /**
    * 初始化单机版的Session并启动计时线程
    */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		if("request".equals(Config.getMode())){                      //如果是request模式，就初始化
			  HttpSession httpSession=HttpSession.getInstance();     //初始化HttpSession
			  Thread thread=new Thread(new DestoryHttpSessionThread());
			  thread.start();                                     //启动定时销毁HttpSession的线程
			  LOG.info("HttpSession已初始化，定时销毁线程已启动！");                                    
		      
		}
		  
	}


	/**
     * 获取sessionId 保证ThreadLocal中有sessionId
     * 1、先去url参数中取找，如果有就得到并设置到threadlocal中
     * 2、再去cookie中去取，能取到就设置到threadlocal，同时初始化session
     * 3、cookie取不到，说明第一次访问并且没有禁用cookie，就创建sessionId，设置到threadlocal并初始化session
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		   String sessionIdParam=request.getParameter("sessionId");    
		   if(sessionIdParam!=null&&!sessionIdParam.equals("")){
				ThreadLocalUtil.set(sessionIdParam);
				LOG.info("过滤完毕,用户禁用cookie,sessionId为："+sessionIdParam);
				chain.doFilter(request, response);
				return;
			}else{
				 String sessionId=CookieUtils.getCookieValue((HttpServletRequest)request, Config.getCookieName());
				    if(sessionId!=null&&!sessionId.equals("")){
				      try {
				    		ThreadLocalUtil.set(sessionId);      //如果cookie中有此sessionID就放入到threadLocal中	  
							SessionManager.set("", "");          //这里是为了防止cookie中有此sessionId但是服务器session中没有的bug，同时也起到刷新时间的作用	   
					} catch (Exception e) {
						e.printStackTrace();
					}
					   CookieUtils.setMaxAge((HttpServletRequest)request, Config.getCookieName(), 0); //删除此cookie 再新建一样的cookie，等于刷新cookie时间
				       CookieUtils.setCookie((HttpServletRequest)request, (HttpServletResponse)response, Config.getCookieName(), sessionId, Integer.parseInt(Config.getSessionValid())*60);
				    }else{
				            String newSessionId=null;
				         try {
				 			   newSessionId = SessionManager.set("", "");     //如果cookie中没有就在服务器创建session，生成uuid并放入ThreadLocal和cookie中
				 			 }catch (Exception e) {
				 				e.printStackTrace();
				 			 } 
				 			  if(newSessionId!=null){
				 				  ThreadLocalUtil.set(newSessionId);
				 			      CookieUtils.setCookie((HttpServletRequest)request, (HttpServletResponse)response, Config.getCookieName(), newSessionId, Integer.parseInt(Config.getSessionValid())*60);
				 			  }  
				    	 }              		      
			  }
		   chain.doFilter(request, response);
		   LOG.info("过滤完毕,sessionID为："+ThreadLocalUtil.get());

	}

}
