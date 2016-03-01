package com.aic.paas.frame.cross.bean;

import java.io.Serializable;

public class DropCondition implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 定义代码 **/
	private String defCode;
	
	
	/** 是否携带属性  **/
	private Boolean addAttrs;
	
	
	/** 是否添加空行 **/
	private Boolean addEmpty;
	
	
	
	public DropCondition() {
	}
	
	public DropCondition(String defCode, Boolean addAttrs, Boolean addEmpty) {
		this.defCode = defCode;
		this.addAttrs = addAttrs;
		this.addEmpty = addEmpty;
	}
	
	
	

	public String getDefCode() {
		return defCode;
	}

	public void setDefCode(String defCode) {
		this.defCode = defCode;
	}

	public Boolean getAddAttrs() {
		return addAttrs;
	}

	public void setAddAttrs(Boolean addAttrs) {
		this.addAttrs = addAttrs;
	}

	public Boolean getAddEmpty() {
		return addEmpty;
	}

	public void setAddEmpty(Boolean addEmpty) {
		this.addEmpty = addEmpty;
	}

	
	
	



	
	
}
