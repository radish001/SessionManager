package TestSessionManager;

import java.util.Date;

import javax.swing.plaf.synth.SynthStyle;

import sessionmanager.HttpSession;

public class TestHttpSession {
   
	public static void main(String[] args) {
		//增加session
		try {
			HttpSession httpSessionManage=HttpSession.getInstance();
			HttpSession.addSession("1", "hi", "world");
			HttpSession.addSession("1", "2", "2");
			//获得最后时间
			System.out.println(HttpSession.getLastTime("1"));
			System.out.println(HttpSession.getLastTime("1"));
			//设置最后时间
			Thread.sleep(5000);
			Date date=new Date();
			HttpSession.setLastTime("1", date);
			System.out.println(HttpSession.getLastTime("1"));
			long start=System.currentTimeMillis();
			//获得value
			System.out.println("第一个："+HttpSession.getValue("1", "hi"));
			System.out.println("第二个："+HttpSession.getValue("1", "2"));
			//设置value
			HttpSession.setValue("1", "hi", "你好");
			System.out.println(HttpSession.getValue("1", "hi"));   //出现log错误
			//判断是否过期
			while(true){
				Thread.sleep(5000);
				boolean isOutTime=HttpSession.isOutTime("1", new Date());	
				
				if(isOutTime==true){
					long end=System.currentTimeMillis();
					int inteval=(int)((end-start)/1000);
					System.out.println("所用时间为："+inteval);
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
