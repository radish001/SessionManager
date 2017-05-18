package sessionmanager;



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
				e.printStackTrace();
				throw e;
			}
          }else if (REQUEST.equals(MODE)) {
                 try {
					sessionId=HttpSessionHandle.set(key, value);
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {	 
					value=HttpSessionHandle.get(key);		
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {
					b=HttpSessionHandle.delete(key);
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
					throw e;
				}
        	}else if(REQUEST.equals(MODE)){
        		try {
					HttpSessionHandle.refreshValid();
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
					throw e;
				}
        	}else if (REQUEST.equals(MODE)) {
				 try {
					b=HttpSessionHandle.destory();
				} catch (Exception e) {
					e.printStackTrace();
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
					e.printStackTrace();
					throw e;
				}
        	}else if(REQUEST.equals(Config.getMode())){
        		try {
					b=HttpSessionHandle.containsSessionId(sessionId);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
        	}
			return b;
        }
        
         
}
