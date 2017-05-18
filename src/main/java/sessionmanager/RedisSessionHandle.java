package sessionmanager;

import org.apache.log4j.Logger;

/**
 * 此类是redissession的分装的顶层类，直接提供各种操作
 * @author hxd
 *
 */

public class RedisSessionHandle {
    private static RedisSession redisSession=RedisSession.getInstance();
	private static final Logger LOG=Logger.getLogger(RedisSessionHandle.class);
    /**
     * 向redissession中设置数据，包括时间
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
	public static String set(String key,Object value) throws Exception{
		String sessionId=null;    
		try {
			sessionId=RedisSession.set(key, value);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		return sessionId;
  }
	
	/**
	 * 获取redissession中的value的方法
	 * @param key     session中的key
	 * @param clazz   要获取对象的类型
	 * @return
	 * @throws Exception
	 */
	public static Object get(String key,Class clazz) throws Exception{
		Object value=null;
		try {
			value=RedisSession.get(key, clazz);
		} catch (Exception e) {
			throw e;
		}
		return value;
	}
	
	
	/**
	 * 删除session中的value
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static boolean delete(String key) throws Exception{
		boolean b=true;
		try {
			b=RedisSession.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return b;
	}
	
	/**
	 * 
	 * 刷新redis中的session的有效时间
	 * @throws Exception
	 */
	public static void refreshValid() throws Exception{
		int seconds=(Integer.parseInt(Config.getSessionValid()))*60;
        try {
			RedisSession.setValidTime(seconds);
		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 销毁一个sessionId对应的redisSession
	 * @return
	 * @throws Exception
	 */
	public static boolean destory() throws Exception{
		try {
			return RedisSession.destory();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 判断是否含有此sessionId
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public static boolean containsSessionId(String sessionId) throws Exception{
		if(sessionId!=null&&!sessionId.equals("")){
			try {
				return RedisSession.containsSessionId(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}else{
			LOG.error("判断时候有此sessionId时参数不合法:"+sessionId);
			throw new Exception("判断时候有此sessionId时参数不合法:"+sessionId);
		}
	}
}
