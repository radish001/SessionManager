package sessionmanager;

/**
 * 放置User对象的本地环境变量
 * @author hxd
 *
 */
public class ThreadLocalUtil {
	private static final ThreadLocal<String> UUID = new ThreadLocal<String>();
	/**
	 * 从本地环境变量中获取值
	 * @return
	 */
	public static String get(){
		return UUID.get();
	}
	
	/**
	 * 向本地环境变量中设置值
	 * @param user
	 */
	
	public static void set(String value){
		UUID.set(value);
	}
	
	
	
}
