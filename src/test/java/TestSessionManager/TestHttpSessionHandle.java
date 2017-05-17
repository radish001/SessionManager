package TestSessionManager;



import javax.servlet.jsp.HttpJspPage;

import sessionmanager.HttpSessionHandle;
import sessionmanager.ThreadLocalUtil;

public class TestHttpSessionHandle {
        public static void main(String[] args) {
			
            //设置session
        	try {
				String uuid=HttpSessionHandle.set("1", "1");
				System.out.println("uuid："+uuid);
				System.out.println("ThreadLocal中的uuid："+ThreadLocalUtil.get());
				
				//或得value
				Object value=HttpSessionHandle.get("1");
				System.out.println("获得的value为："+(String)value);
				
				
				//删除value
				System.out.println(HttpSessionHandle.delete(ThreadLocalUtil.get()));
				System.out.println("删除后ThreadLocal中的uuid："+ThreadLocalUtil.get());
				Object value1=HttpSessionHandle.get("1");
				System.out.println("获得的value为："+(String)value1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        
        }       
}
