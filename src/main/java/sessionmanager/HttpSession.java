package sessionmanager;
/**
 * session容器类 
 * @author hxd
 *
 */

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;


public class HttpSession {
	  //key为sessionId  value为session对象
      private static Map<String, Session> httpSessionMap = new Hashtable<>();
      private static final HttpSession instance=new HttpSession();
      private static Logger LOG = Logger.getLogger(HttpSession.class);
      //此类是单例的
      private HttpSession(){ 
    	  
      }
      
      
      public static HttpSession getInstance(){
    	  return instance;
      }
      
      
         
      
      /**
       * 获取session对象的的方法
       * @param sessionId
       * @return
       * @throws Exception
       */
      public static Session getSession(String sessionId) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")){
    		  return httpSessionMap.get(sessionId);
    	  }
    	  else {
    		  LOG.error(ThreadLocalUtil.get()+":获取session时数据不合法");
			throw new Exception(ThreadLocalUtil.get()+":获取session时数据不合法");
		}
      }
      
      /**
       * 向httpsession中添加session
       * @param sessionId
       * @param key
       * @param value
       * @throws Exception
       */
      public static void addSession(String sessionId,String key,Object value) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")&&key!=null&&value!=null){
    		  if(httpSessionMap.containsKey(sessionId)){   //如果存在此sessionid对应的session就返回此session
    			    Session session=httpSessionMap.get(sessionId);
    			    session.addAttribute(key, value);
    			    session.setLastTime(new Date());
    		  }else{                                        //如果不存在此sessionid对应的session就新建
    			    Session session=new Session();
    			    session.addAttribute(key, value);
    			    session.setLastTime(new Date());
    			    httpSessionMap.put(sessionId,session);
    		  }
    	  }else{
    		  LOG.error(ThreadLocalUtil.get()+":添加session时数据不合法");
    		  throw new Exception(ThreadLocalUtil.get()+":添加session时数据不合法");
    	  }
      }
      
      
      /**
       * 设置最后时间
       * @param sessionId
       * @param date
       * @throws Exception 
       */
      public static void setLastTime(String sessionId,Date date) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")&&date!=null){
    		  if(httpSessionMap.containsKey(sessionId)){
    			  Session session=httpSessionMap.get(sessionId);
        		  session.setLastTime(date);
    		  }else{
    			  LOG.error("没有此sessionId");
    			  throw new Exception("没有此sessionId");
    		  }	  
    	  }else {
    		  LOG.error(ThreadLocalUtil.get()+"：设置最后登录时间时数据不合法");
			 throw new Exception(ThreadLocalUtil.get()+"：设置最后登录时间时数据不合法");
		}
      }
      
      
      /**
       * 获得最后登录时间
       * @param sessionId
       * @return
       * @throws Exception
       */
      public static Date getLastTime(String sessionId) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")){
    		  if(httpSessionMap.containsKey(sessionId)){
    			  Session session=httpSessionMap.get(sessionId);
        		  Date lastTime=session.getLastTime();
        		  if(lastTime!=null){
        			  return lastTime;
        		  }else{
        			  LOG.error(ThreadLocalUtil.get()+"：获取lastTime为null");
        			  throw new Exception(ThreadLocalUtil.get()+"：lastTime为null");
        		  }
    		  }else{
    			  LOG.error("没有此sessionId");
    			  throw new Exception("没有此sessionId");
    		  }
    		  
    	  }else{
 			  throw new Exception(ThreadLocalUtil.get()+"：获取最后登录时间时数据不合法");
    	  }
    	  
      }
      
      /**
       * 获取session对象的value
       * @param sessionId
       * @param key
       * @return
       * @throws Exception
       */
      public static Object getValue(String sessionId,String key) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")&&key!=null){
    		  if(httpSessionMap.containsKey(sessionId)){
    			  Session session=httpSessionMap.get(sessionId);
    			  Object value=session.getAttribute(key);
    			  return value;
    		  }else{
    			  LOG.error("不存在此sessionId");
    			  throw new Exception("不存在此sessionId");
    		  }
    	  }else{
    		  LOG.error(ThreadLocalUtil.get()+"：获取value时数据不合法");
    		  throw new Exception(ThreadLocalUtil.get()+"：获取value时数据不合法");
    	  }
      }
      
      /**
       * 设置session中的value
       * @param sessionId
       * @param key
       * @param value
       * @throws Exception
       */
      public static void setValue(String sessionId,String key,Object value) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")&&key!=null&&value!=null){
    		  if(httpSessionMap.containsKey(sessionId)){
    			  Session session=httpSessionMap.get(sessionId);
        		  session.addAttribute(key, value);
    		  }else{
    			  LOG.error("不存在此sessionId");
    			  throw new Exception("不存在此sessionId");
    		  }
    	  }else{
    		  LOG.error(ThreadLocalUtil.get()+"：设置session中值时数据不合法");
    		  throw new Exception(ThreadLocalUtil.get()+"：设置session中值时数据不合法");
    	  }
    	  
      }
      
      /**
       * 删除指定sessionId的session
       * @param sessionId
       * @return 
       * @throws Exception
       */
      public static boolean delSession(String sessionId) throws Exception{
    	  if(sessionId!=null&&!sessionId.equals("")){
    		  if(httpSessionMap.containsKey(sessionId)){
    			 Session session=httpSessionMap.remove(sessionId);
    			 if(session!=null){
    				return true;
    			 }else{
    				 LOG.error(ThreadLocalUtil.get()+"：删除session时错误");
    				 throw new Exception(ThreadLocalUtil.get()+"：删除session时错误");
    			 }
    		  }else{
    			  LOG.error("不含有此sessionId");
    			  throw new Exception("不含有此sessionId");
    		  }
    	  }else{
    		  LOG.error(ThreadLocalUtil.get()+"：删除session时数据不合法");
    		  throw new Exception(ThreadLocalUtil.get()+"：删除session时数据不合法");
    	  }
    	  
      }
      
      /**
       * 判断是否过期
       * @param date
       * @return
       * @throws Exception 
       */
      public static boolean isOutTime(String sessionId,Date date) throws Exception {
    	  if(sessionId!=null&&!sessionId.equals("")&&date!=null){
    		  long time=date.getTime();
    		  long lastTime=0;
    		  try {
				lastTime=getLastTime(sessionId).getTime();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;	
			}
    		 if(lastTime!=0){
    			long interval=time-lastTime;
    			long sessionValid=(Integer.parseInt(Config.getSessionValid()))*60*1000; //毫秒
    			System.out.println("设置的有效期为："+sessionValid);
    			System.out.println("sessionId为："+sessionId+"  的剩余时间："+(sessionValid-interval));
    			return interval>sessionValid;
    		 }  
    	  }
    	  LOG.error("判断session是否过期时数据不合法");
    	  throw new Exception("判断session是否过期时数据不合法");
      }
      
      /**
       * 根据指定的sessionId和key删除session
       * @param sessionId
       * @param key
       * @return
       * @throws Exception 
       */
      public static boolean delete(String sessionId,String key) throws Exception{
    	  boolean b=true;
    	  if(sessionId!=null&&!sessionId.equals("")&&key!=null){ 
    		  Session session=null;
			try {
				session = HttpSession.getSession(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
    		  if(session!=null){
    			  if(session.containKey(key)){
    				  b=session.delete(key);
    			  }
    		  }else{
    			  b=false;
    		  }
    	  }else{
    		  LOG.error("删除HttpSession时数据不合法");
    		  throw new Exception("删除HttpSession时数据不合法");
    	  }
		return b;
	
      }
      
      /**
       * 判断是否含有指定的sessionId
       * @param sessionId
       * @return
       * @throws Exception
       */
      public static boolean containsSessionId(String sessionId) throws Exception{
    	  if(sessionId!=null){
    		  return httpSessionMap.containsKey(sessionId);
    	  }else{
    		  LOG.error("判断是否存在sessionId时数据不合法");
    		  throw new Exception("判断是否存在sessionId时数据不合法");
    	  }
               
      }
      
    
      
      
      
      
      


	public static Map<String, Session> getHttpSessionMap() {
		return httpSessionMap;
	}


	public static void setHttpSessionMap(Map<String, Session> httpSessionMap) {
		HttpSession.httpSessionMap = httpSessionMap;
	}
      
      
      
      
  
}
