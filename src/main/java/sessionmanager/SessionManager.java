package sessionmanager;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 这是SessionManage的使用类
 * @author 胡晓东
 * @date 2017.5.16
 *
 */
public class SessionManager {
        private static final String MODE=Config.getMode();
        private static final String REDIS="redis";
        private static final String REQUEST="request";
        
        /**
         * 设置session的方法
         * @param key
         * @param value
         * @return sessionId  唯一标识一个session的ID，放入ThreadLocal中
         * @throws Exception 
         */
        public static String set(String key,Object value) throws Exception {
        	String sessionId=null;
        	if(REDIS.equals(MODE)){
        	  try {
				sessionId=RedisSessionHandle.set(key, value);
			} catch (Exception e) {
				throw e;
			}
          }else if (REQUEST.equals(MODE)) {
                 try {
					sessionId=HttpSessionHandle.set(key, value);
				} catch (Exception e) {
					throw e;
				}			
		}
			return sessionId;	
	}
        
        
        
        /**
         * 在session中根据key获取value的方法
         * @param key
         * @param clazz 要获取的对象的class类
         * @return
         * @throws Exception
         */
        public static Object get(String key,Class clazz) throws Exception{
        	Object value=null;
        	if(REDIS.equals(MODE)){
        		try {
					value=RedisSessionHandle.get(key, clazz);
				} catch (Exception e) {
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {	 
					value=HttpSessionHandle.get(key);		
				} catch (Exception e) {
					throw e;
				}
			}
			return value;
        }
        
        /**
         * 在session中根据给定的key删除对应的value
         * @param key
         * @return 删除成功返回true，失败返回false
         * @throws Exception
         */
        public static boolean delete(String key) throws Exception{
        	boolean b=false;
        	if(REDIS.equals(MODE)){
        		try {
					b=RedisSessionHandle.delete(key);
				} catch (Exception e) {
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {
					b=HttpSessionHandle.delete(key);
				} catch (Exception e) {
					throw e;
				}
			}
			return b;
        }
        
        /**
         * 刷新session的有效时间
         * @throws Exception 
         */
        public static void refreshValid() throws Exception{
        	if(REDIS.equals(MODE)){
        		try {
					RedisSessionHandle.refreshValid();
				} catch (Exception e) {
					throw e;
				}
        	}else if(REQUEST.equals(MODE)){
        		try {
					HttpSessionHandle.refreshValid();
				} catch (Exception e) {
					throw e;
				}
        	}
        }
        
        
        /**
         * 销毁该用户的session
         * @return 销毁成功返回true，失败返回false
         * @throws Exception 
         */
        public static boolean destory() throws Exception{
        	boolean b=false;
        	if(REDIS.equals(MODE)){
        		try {
					b=RedisSessionHandle.destory();
				} catch (Exception e) {
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {
					b=HttpSessionHandle.destory();
				} catch (Exception e) {
					throw e;
				}
			}
			return b;
        }
        
        /**
         * 判断服务器session中是否含有此sessionId
         * @param sessionId
         * @return
         * @throws Exception
         */
        public static boolean containsSessionId(String sessionId) throws Exception {
        	boolean b=true;
        	if(REDIS.equals(Config.getMode())){
        		try {
					b=RedisSessionHandle.containsSessionId(sessionId);
				} catch (Exception e) {
					throw e;
				}
        	}else if(REQUEST.equals(Config.getMode())){
        		try {
					b=HttpSessionHandle.containsSessionId(sessionId);
				} catch (Exception e) {
					throw e;
				}
        	}
			return b;
        }
        
        
        
        /**
         * url重写，在url后加上sessionId参数
         * @param url
         * @return
         */
        public static String encodeURL(String url){
        	StringBuilder sendRedirectURL=new StringBuilder();
        	if(url!=null){
                String sessionId=ThreadLocalUtil.get();
        	    if(sessionId!=null){
        	    	//TODO 这里处理的不好 判断转向的url是否带参数
        	    	if(url.contains("?")){                     
        	    		sendRedirectURL.append(url).append("&sessionId="+sessionId);
        	    	}else{
        	    	     sendRedirectURL.append(url).append("?sessionId="+sessionId);
        	    	}
        	    	
        	    }	    	
        	}
			return sendRedirectURL.toString();
        }
        
        
        /**
         * 请求重定向的方法，防止cookie禁用，只能实现重定向后能使用session，不能实现会话级别的session
         * @param response
         * @param URL
         * @throws IOException 
         * @throws Exception
         */
        public static  void sendRedirect(HttpServletResponse response,String url) throws IOException{
        	    String redirectURL=encodeURL(url);
        	    try {
					response.sendRedirect(redirectURL);
				} catch (IOException e) {
			           throw e;
				}
              
       }
}
