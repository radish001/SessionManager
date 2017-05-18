package sessionmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieFilter implements Filter {
    private FilterConfig filterConfig;
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		//得到访问的地址
		String param=(((HttpServletRequest)request).getQueryString())==null?"":"?" + (((HttpServletRequest)request).getQueryString());
		StringBuilder requestUrl=new StringBuilder();
		     requestUrl.append("http://")
		               .append(request.getServerName())
		               .append(":")
		               .append(request.getServerPort())
		               .append(((HttpServletRequest)request).getContextPath())
		               .append(((HttpServletRequest)request).getServletPath())
		               .append(param);
		/*String requestUrlTest = "http://" + request.getServerName() //服务器地址  
                                      + ":"
				                      +request.getServerPort()  //端口号  
                                      + ((HttpServletRequest)request).getContextPath()      //项目名称  
                                      + ((HttpServletRequest)request).getServletPath()      //请求页面或其他地址  
                                      + "?" + (((HttpServletRequest)request).getQueryString()); //参数  
*/		//String redirectUrl=
		ServletContext sc=filterConfig.getServletContext();
		Map<String, Integer> ipMap=(Map<String, Integer>) sc.getAttribute("ipMap");
		if(ipMap==null){                //说明是第一次登陆
			ipMap=new HashMap<>();
		}
		String ip=request.getRemoteAddr();
		if(ipMap.containsKey(ip)){
			int count=ipMap.get(ip);
			count++;
			ipMap.put(ip, count);
		}else{
			ipMap.put(ip, 1);
		}
		
		sc.setAttribute("ipMap", ipMap);
		System.out.println("经过了过滤器cookieFilter");
		int ipCount=((Map<String,Integer>)filterConfig.getServletContext().getAttribute("ipMap")).get(ip);
		System.out.println("访问次数："+ipCount);
		if(ipCount==1){
			CookieUtils.setCookie((HttpServletRequest)request, (HttpServletResponse)response, "TestCookie", "TestCookie", Integer.parseInt(Config.getSessionValid())*60);
			System.out.println("已经设置TestCookie");
			((HttpServletResponse)response).sendRedirect(requestUrl.toString());//再次访问原地址
			return;
		}else if(ipCount==2){
			String testCookieValue=CookieUtils.getCookieValue((HttpServletRequest)request, "TestCookie");
			System.out.println("已经得到TestCookie："+testCookieValue);
			if(testCookieValue==null||"null".equals(testCookieValue)){    //没有拿到testcookie 说明禁用了cookie	 
				String sessionId=UUID.randomUUID().toString();
				System.out.println("检测到用户禁用cookie,设置的sessionId为:"+sessionId);
				ThreadLocalUtil.set(sessionId);
				chain.doFilter(request, response);
			}else{                                                     //拿到了testcookie
				System.out.println("得到了testcookie！！！！！！！！！！！！！！！！！！！！！！");
				chain.doFilter(request, response);
			}	
		}
		//3次以上的请求直接放行
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		    
		this.filterConfig=arg0;
	}

}
