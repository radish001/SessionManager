package sessionmanager;


import java.util.Date;
import java.util.UUID;

/**
 * 对HttpSession的直接操作
 * @author hxd
 *
 */
public class HttpSessionHandle {
 
  
        
        
        /**
         * 利用HttpSession 设置对象
         * @param 
         * @return  sessionId
         * @throws Exception
         */
        public static String set(String key,Object value) throws Exception {
        	if(key!=null&&value!=null){
        		String sessionId=ThreadLocalUtil.get();
        		if(sessionId!=null&&!sessionId.equals("")){
        			try {
        				System.out.println("添加数据的sessionId为："+sessionId);
        				System.out.println("添加数据的线程为："+Thread.currentThread().getName());
        				System.out.println("添加的数据为："+"  key:"+key+"  value:"+value);
						HttpSession.addSession(sessionId, key, value);
						return sessionId;
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
        		}else{
        			//没有uuid 生成uuid 并放入threadlocal并返回
        			String newSessionId=UUID.randomUUID().toString();
        			HttpSession.addSession(newSessionId, key, value);
        			ThreadLocalUtil.set(newSessionId);
        			return newSessionId;
        		}
        	}else{
        		throw new Exception("添加httpsession时数据不合法");
        	}    	 
        }
        
       
        
        
        /**
         * 获取value的方法
         * @param key
         * @return value
         * @throws Exception
         */
        public static Object get(String key) throws Exception{
        	if(key!=null){
        		String sessionId=ThreadLocalUtil.get();
        		if(sessionId!=null){
        			Object value=null;
        			try {
        				value=HttpSession.getValue(sessionId, key);
        				System.out.println("得到数据的sessionId为："+sessionId);
        				System.out.println("得到数据的线程为："+Thread.currentThread().getName());
        				System.out.println("得到的数据为："+"  key:"+key+"  value:"+value);
        				return value;
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
        		}else{
					throw new Exception("sessionId不存在");
				}	
        	}else{
        		throw new Exception("获取value时参数不合法");
        	}
        }
        
       
         
        /**
         * 根据指定sessionId删除session
         * @param key
         * @return  删除成功返回1  删除错误返回0
         * @throws Exception
         */
        public static boolean delete(String key) throws Exception{
        	boolean b=true;
        	if(key!=null){
        		String sessionId=ThreadLocalUtil.get();
        		if(sessionId!=null&&!sessionId.equals("")){
        			  try {
        				  b=HttpSession.delete(sessionId, key);		  
					} catch (Exception e) {
						e.printStackTrace();
                        throw e;
					}	 
        		}else{
        			throw new Exception("不存在此sessionId");
        		}
        	}else{
        		throw new Exception("删除HttpSession时数据不合法");
        	}
			return b;	
        }
        
        
       /**
        * 销毁一个sessionId对应的httpsession 
        * @return
     * @throws Exception 
        */
        public static boolean destory() throws Exception {
        	boolean b=false;
			String sessionId=ThreadLocalUtil.get();
			if(sessionId!=null&&!sessionId.equals("")){
				try {
					b=HttpSession.delSession(sessionId);
					if(b=true){
						ThreadLocalUtil.set("");               //删除该sessionId对应的session后，将ThreadLocal中的sessionId也删除
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}else{
				throw new Exception("不存在此sessionId");
			}
			return b;
		}
        
        
        
        
        /**
         * 刷新httpsession中最后登录时间
         * @throws Exception
         */
        public static void refreshValid() throws Exception{
        	String sessionId=ThreadLocalUtil.get();
        	if(sessionId!=null&&!sessionId.equals("")){
        		try {
					if(HttpSession.containsSessionId(sessionId)){
						HttpSession.setLastTime(sessionId, new Date());
					}else{
						throw new Exception("HttpSessionMap中不存在此sessionId");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
        	}else{
        		throw new Exception("ThreadLocal中不存在此seeeionId");
        	}
        }
        
        
}
