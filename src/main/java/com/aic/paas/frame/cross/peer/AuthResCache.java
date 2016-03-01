package com.aic.paas.frame.cross.peer;



/**
 * 权限资源缓存
 * @author wanwb
 */
public interface AuthResCache {
	
	
	
	/**
	 * 向缓存中添加值
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);
	
	
	
	
	/**
	 * 向缓存中获取值
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	
	
	
	/**
	 * 向缓存在删除值
	 * @param key
	 */
	public void remove(String key);
	
	
	
	
	

}
