package TestSessionManager;

import org.apache.commons.lang.ObjectUtils.Null;

import sessionmanager.DestoryHttpSessionThread;
import sessionmanager.HttpSession;

public class TestDestorySessionThread {
       public static void main(String[] args)  {
		     HttpSession.getInstance();
		 
				try {
					HttpSession.addSession("1", "hi", "i am ok");
					//打印一次
					System.out.println(HttpSession.getValue("1", "hi"));
					Thread thread=new Thread(new DestoryHttpSessionThread());
					thread.start();
					long statr=System.currentTimeMillis();
					while(true){
						Thread.sleep(2000);
						try {
							System.out.println(HttpSession.getValue("1", "hi"));
							long now=System.currentTimeMillis();
							System.out.println("目前时间："+(int)((now-statr)/1000));
						} catch (Exception e) {
							long end=System.currentTimeMillis();
							int inteval=(int)((end-statr)/1000);
							System.out.println("已删除，所用时间："+inteval);
							break;
						}
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
	}
}
