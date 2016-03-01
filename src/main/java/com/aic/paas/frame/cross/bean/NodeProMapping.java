package com.aic.paas.frame.cross.bean;

import java.io.Serializable;

public class NodeProMapping implements Serializable {
	private static final long serialVersionUID = 4411485680957250541L;
	
	
	/** 树节点属性名称 **/
	private String nodeField;
	
	
	/** 映射数据属性名称 **/
	private String dataField;
	
	
	/** 如果映射数据为null时，缺省值 **/
	private Object defaultValue;
	
	
	
	public NodeProMapping() {
	}
	
	public NodeProMapping(String nodeField, String dataField, Object defaultValue) {
		this.nodeField = nodeField;
		this.dataField = dataField;
		this.defaultValue = defaultValue;
	}







	public String getNodeField() {
		return nodeField;
	}


	public void setNodeField(String nodeField) {
		this.nodeField = nodeField;
	}


	public String getDataField() {
		return dataField;
	}


	public void setDataField(String dataField) {
		this.dataField = dataField;
	}


	public Object getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	
	
	
	

}
