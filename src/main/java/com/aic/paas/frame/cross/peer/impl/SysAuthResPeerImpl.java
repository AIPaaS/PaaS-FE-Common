package com.aic.paas.frame.cross.peer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.frame.cross.bean.CSysMenu;
import com.aic.paas.frame.cross.bean.SysFrameUser;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.peer.AuthResCache;
import com.aic.paas.frame.cross.peer.SysAuthResPeer;
import com.aic.paas.frame.cross.peer.SysFramePeer;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;
import com.binary.framework.web.SessionKey;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysAuthResPeerImpl implements SysAuthResPeer {

	
	private final String SYS_AUTH_RES_CACHE_INIT_KEY = "SYS_AUTH_RES_CACHE_INIT_KEY";
	private final String SYS_AUTH_RES_CACHE_MODUIDS_KEY = "SYS_AUTH_RES_CACHE_MODUIDS_KEY";
	private final String SYS_AUTH_RES_CACHE_MENUS_KEY = "SYS_AUTH_RES_CACHE_MENUS_KEY";
	
	
	@Autowired
	SysFramePeer sysFramePeer;
	
	
	@Override
	public void initialization(AuthResCache cache) {
		User user = (User)cache.get(SessionKey.SYSTEM_USER);
		if(user == null) throw new ServiceException(" not found login user! ");
		if(!(user instanceof SysFrameUser)) {
			throw new ServiceException(" the login user '"+user.getClass().getName()+"' not typeof '"+SysFrameUser.class.getName()+"'! ");
		}
		
		SysFrameUser frameUser = (SysFrameUser)user;
		Long[] moduIds = frameUser.getAuthModuIds();
		
		Set<Long> moduIdSet = new HashSet<Long>();
		if(moduIds!=null && moduIds.length>0) {
			for(int i=0; i<moduIds.length; i++) {
				moduIdSet.add(moduIds[i]);
			}
		}
		
		cache.set(SYS_AUTH_RES_CACHE_MODUIDS_KEY, moduIdSet);
		cache.set(SYS_AUTH_RES_CACHE_INIT_KEY, true);
	}
	
	
	protected void verifyLoginInitialization(AuthResCache cache) {
		Boolean ba = (Boolean)cache.get(SYS_AUTH_RES_CACHE_INIT_KEY);
		if(ba==null || !ba.booleanValue()) {
			throw new ServiceException(" not initialization() ");
		}
	}
	
	
	
	protected SysFrameUser getUser(AuthResCache cache) {
		SysFrameUser user = (SysFrameUser)cache.get(SessionKey.SYSTEM_USER);
		if(user == null) throw new ServiceException(" not initialization() ");
		return user;
	}
	
	
	protected Set<Long> getModuIds(AuthResCache cache) {
		Set<Long> set = (Set<Long>)cache.get(SYS_AUTH_RES_CACHE_MODUIDS_KEY);
		if(set == null) throw new ServiceException(" not initialization() ");
		return set;
	}
	
	
	@Override
	public List<SysMenu> getChildMenus(Long parentId, AuthResCache cache) {
		verifyLoginInitialization(cache);
		
		//key=parentId
		Map<Long, List<SysMenu>> map = (Map)cache.get(SYS_AUTH_RES_CACHE_MENUS_KEY);
		if(map == null) map = buildMenuTree(cache);
		
		List<SysMenu> ls = map.get(parentId);
		if(ls == null) ls = new ArrayList<SysMenu>();
		
		return ls;
	}

	
	protected Map<Long, List<SysMenu>> buildMenuTree(AuthResCache cache) {
		synchronized (cache) {
			Map<Long, List<SysMenu>> map = (Map)cache.get(SYS_AUTH_RES_CACHE_MENUS_KEY);
			if(map == null) {
				map = new HashMap<Long, List<SysMenu>>();
				
				SysFrameUser user = getUser(cache);
				Long[] moduIds = user.getAuthModuIds();
				
				if(moduIds!=null && moduIds.length>0) {
					CSysMenu cdt = new CSysMenu();
					cdt.setStatus(1);
					cdt.setModuIds(moduIds);
					List<SysMenu> menus = sysFramePeer.getMenuList(cdt, " ORDER_NO, MENU_CODE ");
					
					//Key=MenuId
					Map<Long, SysMenu> menuMap = new HashMap<Long, SysMenu>();
										
					//将Menu列表转换为key=parentId的Map
					boolean hasRoot = false;
					for(int i=0; i<menus.size(); i++) {
						SysMenu menu = menus.get(i);
						menuMap.put(menu.getId(), menu);
						
						Long parentId = menu.getParentId();
						if(parentId == null) {
							parentId = 0l;
							if(hasRoot) {
								throw new ServiceException(" has multiple root in the sys-menu! ");
							}else {
								hasRoot = true;
							}
							menu.setParentId(parentId);
						}
						
						List<SysMenu> childs = map.get(parentId);
						if(childs == null) {
							childs = new ArrayList<SysMenu>();
							map.put(parentId, childs);
						}
						childs.add(menu);
					}
					
					
					//如果没有根节点, 说明没有一级节点, 权限为空
					if(hasRoot) {
						map.clear();
					}else {
						//去除ParentId在权限菜单列表中不存在的节点
						Long[] parentIds = map.keySet().toArray(new Long[0]);
						for(int i=0; i<parentIds.length; i++) {
							if(parentIds[i] == 0l) continue ;
							
							if(!menuMap.containsKey(parentIds[i])) {
								map.remove(parentIds[i]);
							}
						}
					}
				}
				
				cache.set(SYS_AUTH_RES_CACHE_MENUS_KEY, map);
			}
			
			return map;
		}
	}


	
	
	
	@Override
	public Set<Long> getAuthModuIds(AuthResCache cache) {
		verifyLoginInitialization(cache);
		return (Set<Long>)cache.get(SYS_AUTH_RES_CACHE_MODUIDS_KEY);
	}
	

}
