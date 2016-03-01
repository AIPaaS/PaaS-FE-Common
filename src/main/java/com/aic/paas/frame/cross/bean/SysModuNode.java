package com.aic.paas.frame.cross.bean;

import java.io.Serializable;
import java.util.List;

import com.aic.paas.frame.cross.bean.SysModu;

public class SysModuNode implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** 模块对象 **/
	private SysModu modu;
	
	
	/** 上级节点, 一级则为null **/
	private SysModuNode parent;
	
	
	/** 子节点, 没有子节点则为null **/
	private List<SysModuNode> children;
	
	
	
	public SysModuNode() {
	}
	
	public SysModuNode(SysModu modu, SysModuNode parent) {
		this.modu = modu;
		this.parent = parent;
	}

	
	
	public SysModu getModu() {
		return modu;
	}

	public void setModu(SysModu modu) {
		this.modu = modu;
	}

	public SysModuNode getParent() {
		return parent;
	}

	public void setParent(SysModuNode parent) {
		this.parent = parent;
	}

	public List<SysModuNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysModuNode> children) {
		this.children = children;
	}
	


	
	
	
	
	
	
	
	
	
}
