package sessionmanager;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
/**
 * 配置文件类
 * @author hxd
 *
 */
public class Config {
      private static String mode;
      private static String sessionValid;
      private static String ip;
      private static int port;
      private static int minIdle;
      private static int maxIdle;
      private static int maxTotal;
      private static String cookieName;
      private static final Config instance=new Config();
      private static Logger log = Logger.getLogger(Config.class);
      
      
      
      private Config(){
    	  
      }
      static {
    	  CompositeConfiguration configuration=new CompositeConfiguration();
    	  try {
			configuration.addConfiguration(new PropertiesConfiguration("session.properties"));
			  mode=configuration.getString("mode");
	    	  sessionValid=configuration.getString("session_valid");
			  ip=configuration.getString("redis.ip");
	    	  port=configuration.getInt("redis.port");
	    	  minIdle=configuration.getInt("redis.minIdle");
	    	  maxIdle=configuration.getInt("redis.maxIdle");
	    	  maxTotal=configuration.getInt("redis.maxTotal");	
	    	  cookieName=configuration.getString("cookie_name");
		} catch (ConfigurationException e) {
			e.printStackTrace();
			log.error("读取配置文件错误");
		}
    	    
      }
      
    public static Config getInstance(){
    	return instance;
    }
      
      
	public static String getMode() {
		return mode;
	}
	public static void setMode(String mode) {
		Config.mode = mode;
	}
	
	public static String getSessionValid() {
		return sessionValid;
	}
	public static void setSessionValid(String sessionValid) {
		Config.sessionValid = sessionValid;
	}
	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		Config.ip = ip;
	}
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		Config.port = port;
	}
	public static int getMinIdle() {
		return minIdle;
	}
	public static void setMinIdle(int minIdle) {
		Config.minIdle = minIdle;
	}
	public static int getMaxIdle() {
		return maxIdle;
	}
	public static void setMaxIdle(int maxIdle) {
		Config.maxIdle = maxIdle;
	}
	public static int getMaxTotal() {
		return maxTotal;
	}
	public static void setMaxTotal(int maxTotal) {
		Config.maxTotal = maxTotal;
	}
	
	
	public static String getCookieName() {
		return cookieName;
	}


	public static void setCookieName(String cookieName) {
		Config.cookieName = cookieName;
	}


	public static void main(String[] args) {
		
		System.out.println(Config.getMode());
		System.out.println(Config.getSessionValid());
		System.out.println(Config.getIp());	
		System.out.println(Config.getPort());
		System.out.println(Config.getMinIdle());
		System.out.println(Config.getMaxIdle());
		System.out.println(Config.getMaxTotal());
		System.out.println(Config.getCookieName());
	}
      
}
