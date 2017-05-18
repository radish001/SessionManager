package sessionmanager;
/**
 * 此类管理session中的键值和时间
 * @author hxd
 *
 */

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class Session {
	   
       private  Map<String,Object> sessionMap=new Hashtable<>();       
       private  Date lastTime;
       private static final Logger LOG=Logger.getLogger(Session.class);
       
       /**
        * 设置map的键值
        * @param key
        * @param value
        * @throws Exception 
        */
       public void addAttribute(String key,Object value) {
    		   sessionMap.put(key, value);
    	   
       }
       
       /**
        * 获取值的方法
        * @param key
        * @return
        * @throws Exception
        */
       
       public  Object getAttribute(String key) throws Exception {
    	      if(key!=null){
    	    	  if(sessionMap.containsKey(key)){
    	    		  return sessionMap.get(key);
    	    	  }else{
    	    		  LOG.error(Thread.currentThread().getName()+"线程下"+ThreadLocalUtil.get()+"不存在此key:"+key);
    	    		  throw new Exception(Thread.currentThread().getName()+"线程下"+ThreadLocalUtil.get()+"不存在此key:"+key);
    	    	  }
    	    	  
    	      }
    	      return null;
    		  
    	  
       }
       
       /**
        * 判断是否有指定的key
        * @param key
        * @return
        * @throws Exception
        */
       public  boolean containKey(String key) {
    	  
    		   return sessionMap.containsKey(key);
    	   
       }
       
       /**
        * 删除指定的key的value
        * @param key
        * @return
        */
       public boolean delete(String key){
    	   Object value=sessionMap.remove(key);
    	   if(value!=null){
    		   return true;
    	   }else{
    		   return false;
    	   }	   
       }
       /**
        * 获取所有的key
        * @return
        */
       public  Set<String> getAllKey(){
    	   return sessionMap.keySet();
       }
       
       
      
       
	public Map<String, Object> getSession() {
		return sessionMap;
	}
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}
	public  Date getLastTime() {
		return this.lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
       
       
}
