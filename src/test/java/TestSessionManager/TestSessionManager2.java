package TestSessionManager;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Set;

import sessionmanager.DestoryHttpSessionThread;
import sessionmanager.HttpSession;
import sessionmanager.Session;
import sessionmanager.SessionManager;

public class TestSessionManager2 {
          public static void main(String[] args) {
			User user=new User();
			user.setUserName("huhaha");
			User user2=new User();
			user2.setUserName("radis");
			try {
				//设置
				String uuid=SessionManager.set("1", user);
				String uuid2=SessionManager.set("2", user2);
				System.out.println("uuid:"+uuid);
				System.out.println("uuid2:"+uuid2);
				Thread thread=new Thread(new DestoryHttpSessionThread());
				thread.start();
				//取出
				User getUser=(User)SessionManager.get("1", User.class);
				User getUser2=(User)SessionManager.get("2", User.class);
				System.out.println("getUser:"+getUser);
				System.out.println("getUser2"+getUser2);
				//刷新
				Thread.sleep(20*1000);
				/*SessionManager.refreshValid();
				System.out.println("刷新成功！");*/
				//删除一个
				Thread.sleep(5*1000);
				SessionManager.delete("1");
				Session session=HttpSession.getSession(uuid);
				Set<String> set=session.getAllKey();
				for (String str : set) {
					System.out.println("所有的key："+str);
				}
				//销毁
				SessionManager.destory();
				System.out.println("销毁成功！");
				//再新建
				Thread.sleep(10*1000);
				User user3=new User();
				user3.setUserName("cau");
				String uuid3=SessionManager.set("3", user3);
				System.out.println("uuid3:"+uuid3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
