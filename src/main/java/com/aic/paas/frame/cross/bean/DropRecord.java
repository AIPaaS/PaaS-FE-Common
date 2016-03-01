package com.aic.paas.frame.cross.bean;

import java.io.Serializable;

public class DropRecord implements Serializable {
	private static final long serialVersionUID = 4363877903635540570L;
	
	
	private String code;
	private String name;
	
	private String parentCode;
	private Object attributes;
	
	private String param1;
	private String param2;
	private String param3;

	
	public DropRecord() {
	}
	
	public DropRecord(String code, String name, Object attributes) {
		this.code = code;
		this.name = name;
		this.attributes = attributes;
	}
	
	
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	
	
	
	
	
	
}
