package sessionmanager;



/**
 * 此类是redissession的分装的顶层类，直接提供各种操作
 * @author hxd
 *
 */

public class RedisSessionHandle {
    private static RedisSession redisSession=RedisSession.getInstance();
	
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
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}
}
