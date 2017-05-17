package TestSessionManager;

import java.util.Date;

import sessionmanager.HttpSessionHandle;

public class TestHttpSessionHandle2 {
           public static void main(String[] args) {
			    User user=new User();
			    user.setUserName("wahaha");
			    try {
			    //设置
				String uuid=HttpSessionHandle.set("user", user);
				System.out.println("设置成功，uuid为："+uuid);
				//获取
				User user2=(User)HttpSessionHandle.get("user");
				System.out.println("得到的user："+user2);
				//删除
				Thread.sleep(2000);
				/*System.err.println("删除的结果："+HttpSessionHandle.delete("user"));
				User user3=(User)HttpSessionHandle.get("user");*/
				/*//得到上次时间
				Date last=HttpSession.getLastTime(uuid);
				System.out.println("刷新前上次时间为："+last);
				Thread.sleep(10*1000);
				//刷新
				HttpSessionHandle.refreshValid();
				Date now=HttpSession.getLastTime(uuid);
				System.out.println("刷新后时间为："+now);*/
				//销毁httpsession
				boolean b=HttpSessionHandle.destory();
				System.out.println("销毁的结果："+b);
				HttpSessionHandle.get("user");
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			    
		}
}
