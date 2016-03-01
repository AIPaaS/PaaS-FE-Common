package com.aic.paas.frame.cross.bean;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.binary.core.lang.Conver;
import com.binary.framework.exception.ServiceException;



/**
 * 只支持get方法
 * @author wanwb
 */
public class RequestParameterMap implements Map<String, String> {

	
	private HttpServletRequest request;
	
	
	public RequestParameterMap(HttpServletRequest request) {
		this.request = request;
	}
	
	
	
	@Override
	public int size() {
		throw new ServiceException(" not support size(). ");
	}

	
	
	@Override
	public boolean isEmpty() {
		throw new ServiceException(" not support isEmpty(). ");
	}
	
	

	@Override
	public boolean containsKey(Object key) {
		if(key == null) return false;
		String sk = Conver.to(key, String.class);
		return request.getParameter(sk)!=null || request.getAttribute(sk)!=null;
	}

	
	
	@Override
	public boolean containsValue(Object value) {
		throw new ServiceException(" not support containsValue(Object value). ");
	}
	

	@Override
	public String get(Object key) {
		if(key == null) return null;
		String sk = Conver.to(key, String.class);
		String value = this.request.getParameter(sk);
		if(value == null) {
			Object obj = request.getAttribute(sk);
			if(obj != null) {
				value = Conver.to(obj, String.class);
			}
		}
		return value;
	}

	
	
	@Override
	public String put(String key, String value) {
		throw new ServiceException(" not support put(String key, String value). ");
	}

	
	
	@Override
	public String remove(Object key) {
		throw new ServiceException(" not support remove(Object key). ");
	}

	
	
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		throw new ServiceException(" not support putAll(Map<? extends String, ? extends String> m). ");
	}

	
	
	@Override
	public void clear() {
		throw new ServiceException(" not support clear(). ");
	}

	
	
	@Override
	public Set<String> keySet() {
		throw new ServiceException(" not support keySet(). ");
	}
	
	
	

	@Override
	public Collection<String> values() {
		throw new ServiceException(" not support values(). ");
	}

	
	
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		throw new ServiceException(" not support entrySet(). ");
	}
	
	
	

}
