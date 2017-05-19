package sessionmanager;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 检查HttpSession是否过期，如果过期就删除
 * @author 胡晓东
 * @date 2017.5.12
 *
 */
public class DestoryHttpSessionThread implements Runnable{
	private static Logger LOG = Logger.getLogger(DestoryHttpSessionThread.class);
	@Override
	public void run() {
		while(true){
			
			try {
				Thread.sleep(5*1000);
				destoryHttpSession();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("销毁HttpSession线程错误");
			}
		}
		
	}
	/**
	 * 检查HttpSession是否过期，过期就删除
	 * @throws Exception
	 */
	public void destoryHttpSession() throws Exception {
	    Map<String, Session> httpSessionMap=HttpSession.getHttpSessionMap();
	    Iterator<Map.Entry<String, Session>> it=httpSessionMap.entrySet().iterator();
	    while(it.hasNext()){
	         Map.Entry<String, Session> entry=it.next();
	         String sessionId=entry.getKey();
	         try {
				if(HttpSession.isOutTime(sessionId, new Date())){
					 it.remove();
					 LOG.info("sessionId为："+sessionId+"的httpsession销毁成功");
					 System.out.println("sessionId为："+sessionId+"的httpsession销毁成功");
				 }
			} catch (Exception e) {
				throw e;
			}
	    }
	}

}
