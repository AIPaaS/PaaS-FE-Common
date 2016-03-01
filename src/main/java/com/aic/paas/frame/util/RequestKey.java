package com.aic.paas.frame.util;

import java.util.HashSet;
import java.util.Set;

import com.binary.core.util.SecuritySet;

public abstract class RequestKey {
	
	
	
	public static final String DYNAMIC_MODUINFO_KEY = "DYNAMIC_MODUINFO";
	
	
	
	public static final Set<String> DYNAMIC_IGNORE_KEYS;
	
	
	static {
		Set<String> set = new HashSet<String>();
		
		set.add("javax.servlet.forward.request_uri");
		set.add("javax.servlet.forward.context_path");
		set.add("javax.servlet.forward.servlet_path");
		set.add("javax.servlet.forward.query_string");
		set.add("org.apache.struts.action.MESSAGE");
		set.add("org.apache.struts.action.mapping.instance");
		set.add("org.apache.struts.action.MODULE");
		
		set.add("org.springframework.web.servlet.HandlerMapping.introspectTypeLevelMapping");
		set.add("org.springframework.web.servlet.DispatcherServlet.FLASH_MAP_MANAGER");
		set.add("org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP");
		set.add("org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping.useDefaultSuffixPattern");
		set.add("org.springframework.web.servlet.DispatcherServlet.THEME_SOURCE");
		set.add("org.springframework.web.servlet.DispatcherServlet.THEME_RESOLVER");
		set.add("org.springframework.web.servlet.DispatcherServlet.CONTEXT");
		set.add("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
		set.add("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
		set.add("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
		set.add("org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER");
		
		set.add(DYNAMIC_MODUINFO_KEY);
		
		DYNAMIC_IGNORE_KEYS = new SecuritySet<String>(set);
	}

}
