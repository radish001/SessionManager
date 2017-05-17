package TestSessionManager;

import sessionmanager.RedisSessionHandle;

public class TestRedisSessionHandle {
    public static void main(String[] args) {
		User user=new User();
		user.setUserName("radish");
		try {
			//设置
			String uuid=RedisSessionHandle.set("name", user);
			System.out.println("设置成功uuid为："+uuid);
			//获取
			User user2=(User)RedisSessionHandle.get("name", User.class);
			System.out.println("得到的user为："+user2);
			//删除
			/*Thread.sleep(2000);
			boolean b=RedisSessionHandle.delete("name");
			System.out.println("删除的结果："+b);*/
			//刷新
			Thread.sleep(10*1000);
			RedisSessionHandle.refreshValid();
			System.out.println("刷新成功！");
			Thread.sleep(20*1000);
			RedisSessionHandle.refreshValid();
			System.out.println("再次刷新成功！");
			//销毁
			System.out.println("开始销毁");
			Thread.sleep(5000);
			boolean b=RedisSessionHandle.destory();
			System.out.println("销毁redisSesion的结果："+b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
