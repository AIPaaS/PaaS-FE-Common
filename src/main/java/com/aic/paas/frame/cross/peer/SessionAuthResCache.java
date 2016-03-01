package com.aic.paas.frame.cross.peer;

import javax.servlet.http.HttpSession;

public class SessionAuthResCache implements AuthResCache {
	
	
	private HttpSession session;
	
	public SessionAuthResCache(HttpSession session) {
		this.session = session;
	}
	@Override
	public void set(String key, Object value) {
		this.session.setAttribute(key, value);
	}
	@Override
	public Object get(String key) {
		return this.session.getAttribute(key);
	}
	@Override
	public void remove(String key) {
		this.session.removeAttribute(key);;
	}
	

}
