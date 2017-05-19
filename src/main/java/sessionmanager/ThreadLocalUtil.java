package sessionmanager;

/**
 * 放置对象到本地环境变量的工具类
 * @author 胡晓东
 * @date 2017.5.15
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
