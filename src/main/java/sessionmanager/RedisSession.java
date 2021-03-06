package sessionmanager;




import java.util.UUID;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 此类是对redis的基本操作
 * @author 胡晓东
 * @date 2017.5.15
 *
 */
public class RedisSession {

	  private static JedisPool pool=null;
	  private static final RedisSession INSTANCE=new RedisSession();
	  private static final ObjectMapper MAPPER=new ObjectMapper();
	  private static final Logger LOG=Logger.getLogger(RedisSession.class);
	  
	  private RedisSession(){
		  
	  }
	  public static RedisSession getInstance(){
		  return INSTANCE;
	  }
	  
      static{
    	  if(pool==null){
    		  JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        	  jedisPoolConfig.setMaxTotal(Config.getMaxTotal());
        	  jedisPoolConfig.setMaxIdle(Config.getMinIdle());
        	  jedisPoolConfig.setMinIdle(Config.getMinIdle());
        	  jedisPoolConfig.setTestOnBorrow(true);
        	  pool=new JedisPool(jedisPoolConfig, Config.getIp(), Config.getPort()); 
    	  }
    	  
      }
      
     /**
      * 将jedis对象返回给连接池 
      * @param pool  连接池
      * @param jedis  要返回的jedis对象
      */
     @SuppressWarnings("deprecation")
	public static void returnResource(JedisPool pool,Jedis jedis){
    	  if(jedis!=null && pool!=null){
    		  pool.returnResource(jedis);
    	  }
      }
     
     
     
     /**
      * 向redis添加数据 使用配置文件中有效时间
      * @param key
      * @param value
      * @throws Exception 
      */
     public static String set(String key,Object value) throws Exception{
    	 Jedis jedis=pool.getResource();
    	 String sessionId=ThreadLocalUtil.get();          
    	 if(sessionId!=null&&!sessionId.equals("")){
    		 long ret=jedis.hset(sessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
        	 jedis.expire(sessionId,Integer.parseInt(Config.getSessionValid())*60);//设置生存时间
    		 if(ret==0||ret==1){
    			 LOG.info("添加数据的sessionId："+sessionId+" key:"+key+" value:"+MAPPER.writeValueAsString(value));
    			 LOG.info("添加数据的线程为："+Thread.currentThread().getName());
    			 returnResource(pool, jedis); 
        		 return sessionId;
        	 }else{
        		 pool.returnBrokenResource(jedis);
        		 LOG.error("RedisSession添加数据失败");
        		 throw new Exception("RedisSession添加数据失败");
        	 }	
    	 }else{
    		 String newSessionId=UUID.randomUUID().toString();   //如果不存在sessionId 新生成，并设置到threadlocal中
    		 LOG.info("新创建sessionId："+newSessionId);
    		 ThreadLocalUtil.set(newSessionId);    //把sessionId放入threadlocal中
    		 long ret=jedis.hset(newSessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
    		 jedis.expire(newSessionId,Integer.parseInt(Config.getSessionValid())*60);//设置生存时间
    		 if(ret==0||ret==1){
    			 LOG.info("添加的数据sessionId："+newSessionId+" key:"+key+" value:"+MAPPER.writeValueAsString(value));
    			 LOG.info("添加数据的线程为："+Thread.currentThread().getName());
    			 returnResource(pool, jedis); 
        		 return newSessionId;
        	 }else{
        		 pool.returnBrokenResource(jedis);
        		 LOG.error("RedisSession添加数据失败");
        		 throw new Exception("RedisSession添加数据失败");
        	 }	
    	 }
    	   
     }
     
    /**
     * 向redis中添加数据，包括传入有效时间
     * @param key    键
     * @param value  值
     * @param validTime   有效时间
     * @throws Exception 
     */
     public static String set(String key,Object value,int seconds) throws Exception{
    	 Jedis jedis=pool.getResource();
    	 String sessionId=ThreadLocalUtil.get();          
    	 if(sessionId!=null&&!sessionId.equals("")){
    		 long ret=jedis.hset(sessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
        	 jedis.expire(sessionId,seconds);//设置生存时间
    		 if(ret==0||ret==1){
    			 returnResource(pool, jedis); 
        		 return sessionId;
        	 }else{
        		 pool.returnBrokenResource(jedis);
        		 LOG.error("RedisSession添加数据失败");
        		 throw new Exception("RedisSession添加数据失败");
        	 }	
    	 }else{
    		 String newSessionId=UUID.randomUUID().toString();   //如果不存在sessionId 新生成，并设置到threadlocal中
    		 ThreadLocalUtil.set(newSessionId);  
    		 long ret=jedis.hset(newSessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
        	 jedis.expire(newSessionId,seconds);//设置生存时间
    		 if(ret==0||ret==1){
    			 returnResource(pool, jedis); 
        		 return newSessionId;
        	 }else{
        		 pool.returnBrokenResource(jedis);
        		 LOG.error("RedisSession添加数据失败");
        		 throw new Exception("RedisSession添加数据失败");
        	 }	
    	 }
    	   
    }
     
     /**
      * 向redis中添加数据没有过期时间
      * @param key    键
      * @param value  值
      * @param validTime   有效时间
      * @throws Exception 
      */
      public static String setWithOutValid(String key,Object value) throws Exception{
     	 Jedis jedis=pool.getResource();
     	 String sessionId=ThreadLocalUtil.get();          
     	 if(sessionId!=null&&!sessionId.equals("")){
     		 long ret=jedis.hset(sessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
     		 if(ret==0||ret==1){
     			returnResource(pool, jedis); 
        		 return sessionId;
         	 }else{
         		 pool.returnBrokenResource(jedis);
         		 LOG.error("RedisSession添加数据失败");
         		 throw new Exception("RedisSession添加数据失败");
         	 }	
     	 }else{
     		 String newSessionId=UUID.randomUUID().toString();   //如果不存在sessionId 新生成，并设置到threadlocal中
     		 ThreadLocalUtil.set(newSessionId);  
     		 long ret=jedis.hset(newSessionId, key, MAPPER.writeValueAsString(value)); //如果filed已经存在，返回0，新建返回1
     		 if(ret==0||ret==1){
     			 returnResource(pool, jedis); 
         		 return newSessionId;
         	 }else{
         		 pool.returnBrokenResource(jedis);
         		 LOG.error("RedisSession添加数据失败");
         		 throw new Exception("RedisSession添加数据失败");
         	 }	
     	 }
     	   
     }
     
     
     
     
     /**
      * 按指定的sessionId设置有效时间
      * @param sessionId
      * @param validTime
      * @throws Exception
      */
     public static void setValidTime(String sessionId,int seconds) throws Exception{
   	     Jedis jedis=pool.getResource();
          if(!jedis.exists(sessionId)){
        	  pool.returnBrokenResource(jedis);
        	  LOG.error("当前sessionId不存在:"+sessionId);
        	  throw new Exception("当前sessionId不存在:"+sessionId);    	  
          }
           jedis.expire(sessionId, seconds);
           returnResource(pool, jedis);
   } 
     
     /**
      * 设置有效时间 不指定sessionId
      * @param sessionId
      * @param validTime
      * @throws Exception
      */
     public static void setValidTime(int seconds) throws Exception{
   	     Jedis jedis=pool.getResource();
         String sessionId=ThreadLocalUtil.get();
         if(sessionId!=null&&!sessionId.equals("")){
            if(jedis.exists(sessionId)){
            	jedis.expire(sessionId, seconds);
            }else{
            	throw new Exception("redis中不存在此sessionId:"+sessionId);
            }
        }else{
        	throw new Exception("ThreadLocal中不存在此sessionId:"+sessionId);
        } 
 }  
     
     /**
      * 从redis中获取值 
      * @param 
      * @param key   要获取对象的class
      * @return   
      * @throws Exception 
      */
     public synchronized static Object get(String key,Class clazz) throws Exception{
	   	   Jedis jedis=pool.getResource();
    	   Object value=null;
    	   String sessionId=ThreadLocalUtil.get();
    	   if(sessionId!=null&&!sessionId.equals("")){
    		   if(jedis.exists(sessionId)){
    			   String jsonDate=jedis.hget(sessionId, key);
    			   if(jsonDate!=null){
    				   LOG.info("得到数据的sessionId："+sessionId);
    				   LOG.info("得到的jsonDate为："+jsonDate);
    				   LOG.info("得到数据的线程为："+Thread.currentThread().getName());
            		   value=MAPPER.readValue(jsonDate, clazz);
            		   returnResource(pool, jedis);
            		   return value;
    			   }else{
    				   LOG.info(sessionId+"下不存在此key:"+key);
    				   System.out.println("当前线程："+Thread.currentThread().getName());
    				   throw new Exception(sessionId+"下不存在此key:"+key);   
    			   }
    		   }else{
    			   LOG.info("redis中不存在此sessionId:"+sessionId);
    			   throw new Exception("redis中不存在此sessionId:"+sessionId);
    		   }
    	   }else{
    		   pool.returnBrokenResource(jedis);
    		   LOG.info("ThreadLocal中不存在此sessionId:"+sessionId);
    		   throw new Exception("ThreadLocal中不存在此sessionId:"+sessionId);
    	   }	   
     }  
     
     
     
   /**
    * 从redis中删除指定数据
    * @param key
    * @return  
    * @throws Exception
    */
     public static boolean delete(String key) throws Exception{ 
    	 Jedis jedis=pool.getResource();
    	 String sessionId=ThreadLocalUtil.get();
    	 if(sessionId!=null&&!sessionId.equals("")){
    		 long ret=jedis.hdel(sessionId, key);
             returnResource(pool, jedis);
             return ret==1? true:false;
    	 }else{
    		 pool.returnBrokenResource(jedis);
    		 LOG.error("sessionId不正确:"+sessionId);
    		 throw new Exception("sessionId不正确:"+sessionId);  		 
    	 }            
     }
     /**
      * 销毁一个sessionId对应的redisSession
      * @return
      * @throws Exception 
      */
     public static boolean destory() throws Exception{
    	 Jedis jedis=pool.getResource();
    	 String sessionId=ThreadLocalUtil.get();
    	 if(sessionId!=null&&!sessionId.equals("")){
    		long ret=jedis.del(sessionId);
    		 returnResource(pool, jedis);
    		return ret>0?  true:false;
    	 }else{
    		 pool.returnBrokenResource(jedis);
    		 LOG.error("sessionId不正确:"+sessionId);
    		 throw new Exception("sessionId不正确:"+sessionId);  
    	 }
     }
     
     
     /**
      * 判断是否含有此sessionId
      * @param sessionId
      * @return
      * @throws Exception
      */
     public static boolean containsSessionId(String sessionId) throws Exception{
    	 boolean b=true;
    	 if(sessionId!=null&&!sessionId.equals("")){
    		 Jedis jedis=pool.getResource();
    		 b=jedis.exists(sessionId);
    		 returnResource(pool, jedis);
    		 return b;
    	 }else{
    		 LOG.error("判断sessionId是否存在时参数不合法");
    		 throw new Exception("判断sessionId是否存在时参数不合法"); 
    	 }
     }
     
 }
     
     
     
     
     
     

