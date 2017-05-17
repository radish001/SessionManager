package TestSessionManager;

import java.util.UUID;

import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;

import sessionmanager.RedisSession;



public class TestRedisSession {
	  public static void main(String[] args) {
		 //get();
		  //delete();
		  setWithOutTime();
	}
	  
	  
	  //测试添加数据
	  public static String set(String key,Object value){
		  //设置数据
		  User user=new User();
		  user.setUserName("radish");
		  String uuid=null;
		  try {
			uuid=RedisSession.set("hahaha", user);
			System.out.println("uuid:"+uuid);
			
			//Object object=RedisSession.get(key, clazz)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return uuid;
	  }
	  
	 //测试获取数据
	  public static void get(){
		  User user=new User();
		  user.setUserName("hahahahaha");
		  try {
		    String uuid=RedisSession.set("1",user);
		    System.out.println("uuid:"+uuid);
		    Object object=RedisSession.get("1", User.class);
		    User user2=(User)object;
		    System.out.println("得到的user："+user2);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	  }
	  
	  //测试删除数据
	  public static void delete(){
		  User user=new User();
		  user.setUserName("hhihihhiihhi");
		  String uuid;
		try {
			uuid = RedisSession.set("1",user);
			System.out.println("uuid:"+uuid);
			boolean bool=RedisSession.delete("1");
			System.out.println("删除的结果："+bool);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	  }
	  
	  //测试不添加生存时间
	  public static void setWithOutTime(){
		  User user=new User();
		  user.setUserName("hhhhhhhhh");
		  try {
			String uuid=RedisSession.setWithOutValid("222", user);
			System.out.println("uuid:"+uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
