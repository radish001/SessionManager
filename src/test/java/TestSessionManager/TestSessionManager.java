package TestSessionManager;

import sessionmanager.SessionManager;

public class TestSessionManager {
        public static void main(String[] args) {
			User user=new User();
			user.setUserName("huhaha");
			User user2=new User();
			user2.setUserName("radish");
			
			try {
				//设置
				String uuid=SessionManager.set("1", user);
				String uuid2=SessionManager.set("2", user2);
				System.out.println("uuid："+uuid);
				System.out.println("uuid2："+uuid2);
				//获取
				User getUser1=(User)SessionManager.get("1", User.class);
				User getUser2=(User)SessionManager.get("2", User.class);
				System.out.println("getUser1:"+getUser1);
				System.out.println("getUser2:"+getUser2);
				//刷新时间
				Thread.sleep(20*1000);
				SessionManager.refreshValid();
				//删除一个
				
				SessionManager.delete("1");
				System.out.println("删除成功！");
				//销毁
				SessionManager.destory();
				System.out.println("销毁成功！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
