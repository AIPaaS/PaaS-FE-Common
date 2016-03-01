package com.aic.paas.frame.cross.bean;

import java.io.Serializable;
import java.util.List;


/**
 * 树节点对象
 * @author wanwb
 */
public class TreeNode implements Serializable {
	private static final long serialVersionUID = 2683712370737589328L;
	
	
	private String id;
	private String text;
	private Integer leaf;		//是否末级	1=是		0=不是
	private String icon;
	private Boolean checked;
	private String parentid;
	private String param1;
	private String param2;
	private String param3;
	private String cls;
	
	private String uiProvider;		//ColumnTree
	private String subexp;			//ColumnTree
	private String bindexp;			//ColumnTree
	private String head;			//ColumnTree
	
	
	private Object attributes;
	
	private List<TreeNode> childNodes;		//子节点

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	


	public Integer getLeaf() {
		return leaf;
	}


	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Boolean getChecked() {
		return checked;
	}


	public void setChecked(Boolean checked) {
		this.checked = checked;
	}


	public String getParentid() {
		return parentid;
	}


	public void setParentid(String parentid) {
		this.parentid = parentid;
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


	public String getCls() {
		return cls;
	}


	public void setCls(String cls) {
		this.cls = cls;
	}


	public String getUiProvider() {
		return uiProvider;
	}


	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}


	public String getSubexp() {
		return subexp;
	}


	public void setSubexp(String subexp) {
		this.subexp = subexp;
	}


	public String getBindexp() {
		return bindexp;
	}


	public void setBindexp(String bindexp) {
		this.bindexp = bindexp;
	}


	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		this.head = head;
	}


	public Object getAttributes() {
		return attributes;
	}


	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}


	public List<TreeNode> getChildNodes() {
		return childNodes;
	}


	public void setChildNodes(List<TreeNode> childNodes) {
		this.childNodes = childNodes;
	}
	
	
	
	
	
	
}
