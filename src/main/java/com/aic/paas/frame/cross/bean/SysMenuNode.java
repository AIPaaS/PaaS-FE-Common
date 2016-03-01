package com.aic.paas.frame.cross.bean;

import java.io.Serializable;
import java.util.List;

import com.aic.paas.frame.cross.bean.SysMenu;

public class SysMenuNode implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** 菜单对象 **/
	private SysMenu menu;
	
	
	/** 上级节点, 一级则为null **/
	private SysMenuNode parent;
	
	
	/** 子节点, 没有子节点则为null **/
	private List<SysMenuNode> children;
	
	
	
	public SysMenuNode() {
	}
	
	public SysMenuNode(SysMenu menu, SysMenuNode parent) {
		this.menu = menu;
		this.parent = parent;
	}
	


	public SysMenu getMenu() {
		return menu;
	}


	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}


	public SysMenuNode getParent() {
		return parent;
	}


	public void setParent(SysMenuNode parent) {
		this.parent = parent;
	}

	public List<SysMenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuNode> children) {
		this.children = children;
	}


	
	
	
	
	
	
	
}
