package com.aic.paas.frame.cross.integration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysMenuNode;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysModuNode;
import com.binary.core.util.SecurityList;

public class AuthorityPreview implements Serializable {
	private static final long serialVersionUID = 1L;

	
	/** 权限菜单 **/
	private List<SysMenu> menus;
	
	
	/** 权限模块 **/
	private List<SysModu> modus;
	
	
	
	/** 菜单节点, key=menuId **/
	private final Map<Long, SysMenuNode> menuNodeMap = new HashMap<Long, SysMenuNode>();
	
	
	/** 菜单节点, key=parentId, 一级节点parentId=0 **/
	private final Map<Long, List<SysMenuNode>> parentMenuNodeMap = new HashMap<Long, List<SysMenuNode>>();
	
	
	/** 模块节点, key=moduId **/
	private final Map<Long, SysModuNode> moduNodeMap = new HashMap<Long, SysModuNode>();
	
	
	/** 模块节点, key=parentId, 一级节点parentId=0 **/
	private final Map<Long, List<SysModuNode>> parentModuNodeMap = new HashMap<Long, List<SysModuNode>>();
	
	
	
	/** 模块对应菜单关系, key=moduId, 如果模块没有对应的菜单则找父节点对应菜单 **/
	private final Map<Long, SysMenuNode> moduMenuRltMap = new HashMap<Long, SysMenuNode>();
	
	
	
	public AuthorityPreview(List<SysMenu> menus, List<SysModu> modus) {
		this.menus = menus;
		this.modus = modus;
		init();
	}
	
	
	
	private void init() {
		parseMenuTree();
		parseModuTree();
		parseModuMenuRlt();
	}
	
	
	
	
	/**
	 * 解析菜单树
	 */
	private void parseMenuTree() {
		for(int i=0; i<menus.size(); i++) {
			SysMenu menu = menus.get(i);			
			Long parentId = menu.getParentId();
			if(parentId == null) menu.setParentId(parentId=0l);
			
			SysMenuNode node = new SysMenuNode(menu, null);
			menuNodeMap.put(menu.getId(), node);
		}
		
		//设置上级节点
		for(int i=0; i<menus.size(); i++) {
			SysMenu menu = menus.get(i);
			Long id = menu.getId();
			Long parentId = menu.getParentId();
			if(parentId.longValue()!=0 && !menuNodeMap.containsKey(parentId)) {
				continue ;
			}
			
			List<SysMenuNode> ls = parentMenuNodeMap.get(parentId);
			if(ls == null) {
				ls = new ArrayList<SysMenuNode>();
				parentMenuNodeMap.put(parentId, ls);
			}
			
			SysMenuNode node = menuNodeMap.get(id);
			ls.add(node);
			
			if(parentId.longValue() != 0) {
				node.setParent(menuNodeMap.get(parentId));
			}
		}
		
		//设置子节点
		Iterator<Entry<Long, List<SysMenuNode>>> itor = parentMenuNodeMap.entrySet().iterator();
		while(itor.hasNext()) {
			Entry<Long, List<SysMenuNode>> e = itor.next();
			Long parentId = e.getKey();
			List<SysMenuNode> childs = e.getValue();
			
			if(parentId.longValue() != 0) {
				menuNodeMap.get(parentId).setChildren(childs);
			}
		}
	}
	
	
	
	/**
	 * 解析模块树
	 */
	private void parseModuTree() {
		for(int i=0; i<modus.size(); i++) {
			SysModu modu = modus.get(i);			
			Long parentId = modu.getParentId();
			if(parentId == null) modu.setParentId(parentId=0l);
			
			SysModuNode node = new SysModuNode(modu, null);
			moduNodeMap.put(modu.getId(), node);
		}
		
		//设置上级节点
		for(int i=0; i<modus.size(); i++) {
			SysModu modu = modus.get(i);
			Long id = modu.getId();
			Long parentId = modu.getParentId();
			if(parentId.longValue()!=0 && !moduNodeMap.containsKey(parentId)) {
				continue ;
			}
			
			List<SysModuNode> ls = parentModuNodeMap.get(parentId);
			if(ls == null) {
				ls = new ArrayList<SysModuNode>();
				parentModuNodeMap.put(parentId, ls);
			}
			
			SysModuNode node = moduNodeMap.get(id);
			ls.add(node);
			
			if(parentId.longValue() != 0) {
				node.setParent(moduNodeMap.get(parentId));
			}
		}
		
		//设置子节点
		Iterator<Entry<Long, List<SysModuNode>>> itor = parentModuNodeMap.entrySet().iterator();
		while(itor.hasNext()) {
			Entry<Long, List<SysModuNode>> e = itor.next();
			Long parentId = e.getKey();
			List<SysModuNode> childs = e.getValue();
			
			if(parentId.longValue() != 0) {
				moduNodeMap.get(parentId).setChildren(childs);
			}
		}
	}
	
	
	
	
	/**
	 * 解析模块与菜单的关系
	 */
	private void parseModuMenuRlt() {
		Iterator<SysMenuNode> itor = menuNodeMap.values().iterator();
		while(itor.hasNext()) {
			SysMenuNode menu = itor.next();
			moduMenuRltMap.put(menu.getMenu().getModuId(), menu);
		}
		
		List<SysModuNode> firsts = parentModuNodeMap.get(0l);
		if(firsts!=null && firsts.size()>0) {
			for(int i=0; i<firsts.size(); i++) {
				SysModuNode f = firsts.get(i);
				List<SysModuNode> childs = f.getChildren();
				if(childs!=null && childs.size()>0) {
					parseModuMenuRlt(childs);
				}
			}
		}
	}
	
	private void parseModuMenuRlt(List<SysModuNode> moduNodes) {
		for(int i=0; i<moduNodes.size(); i++) {
			SysModuNode node = moduNodes.get(i);
			Long moduId = node.getModu().getId();
			if(!moduMenuRltMap.containsKey(moduId)) {
				List<SysModuNode> parents = getModuLevelParentNodes(moduId);
				for(int j=1; j<parents.size(); j++) {
					SysModuNode pn = parents.get(j);
					Long pid = pn.getModu().getId();
					
					SysMenuNode menuNode = moduMenuRltMap.get(pid);
					if(menuNode != null) {
						moduMenuRltMap.put(moduId, menuNode);
						break;
					}
				}
			}
			
			
			List<SysModuNode> childs = node.getChildren();
			if(childs!=null && childs.size()>0) {
				parseModuMenuRlt(childs);
			}
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 跟据菜单ID获取菜单节点
	 * @param menuId
	 * @return
	 */
	public SysMenuNode getMenuNodeById(Long menuId) {
		return menuNodeMap.get(menuId);
	}
	
	
	
	
	/**
	 * 跟据模块ID获取对应的菜单节点, 如果当前模块节点没有对应的菜单节点, 则找父节点对应的菜单节点
	 * @param moduId
	 * @return
	 */
	public SysMenuNode getMenuNodeByModuId(Long moduId) {
		return moduMenuRltMap.get(moduId);
	}
	
	
	
	/**
	 * 跟据模块ID获取模块节点
	 * @param moduId
	 * @return
	 */
	public SysModuNode getModuNodeById(Long moduId) {
		return moduNodeMap.get(moduId);
	}
	
	
	
	
	/**
	 * 跟据菜单上级节点获取子节点
	 * @param parentMenuId 为0表示获取一级
	 * @return
	 */
	public List<SysMenuNode> getMenuChildNodes(Long parentMenuId) {
		List<SysMenuNode> ls = parentMenuNodeMap.get(parentMenuId);
		return ls==null ? null : new SecurityList<SysMenuNode>(ls);
	}
	
	
	
	/**
	 * 跟据模块上级节点获取子节点
	 * @param parentModuId 为0表示获取一级
	 * @return
	 */
	public List<SysModuNode> getModuChildNodes(Long parentModuId) {
		List<SysModuNode> ls = parentModuNodeMap.get(parentModuId);
		return ls==null ? null : new SecurityList<SysModuNode>(ls);
	}
	
	
	
	
	/**
	 * 跟据菜单ID获取上级层级菜单
	 * @param menuId
	 * @return [0]=当前菜单		[length-1]=最顶级菜单
	 */
	public List<SysMenuNode> getMenuLevelParentNodes(Long menuId) {
		List<SysMenuNode> parentNodes = new ArrayList<SysMenuNode>();
		SysMenuNode node = menuNodeMap.get(menuId);
		if(node != null) {
			parentNodes.add(node);
			SysMenuNode parentNode = null;
			while((parentNode=node.getParent()) != null) {
				parentNodes.add(parentNode);
				node = parentNode;
			}
		}
		return parentNodes;
	}
	
	
	
	
	/**
	 * 跟据模块ID获取上级层级模块
	 * @param moduId
	 * @return [0]=当前模块		[length-1]=最顶级模块
	 */
	public List<SysModuNode> getModuLevelParentNodes(Long moduId) {
		List<SysModuNode> parentNodes = new ArrayList<SysModuNode>();
		SysModuNode node = moduNodeMap.get(moduId);
		if(node != null) {
			parentNodes.add(node);
			SysModuNode parentNode = null;
			while((parentNode=node.getParent()) != null) {
				parentNodes.add(parentNode);
				node = parentNode;
			}
		}
		return parentNodes;
	}
	
	
	
	
	
	
	
	
	
}
