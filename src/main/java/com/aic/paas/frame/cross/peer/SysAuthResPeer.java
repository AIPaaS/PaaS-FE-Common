package com.aic.paas.frame.cross.peer;

import java.util.List;
import java.util.Set;

import com.aic.paas.frame.cross.bean.SysMenu;



/**
 * 系统权限资源管理
 * @author wanwb
 */
public interface SysAuthResPeer {
	
	
	
	
	/**
	 * 用户登录时需要初始化此方法
	 * @param cache
	 */
	public void initialization(AuthResCache cache);
	
	
		
	
	
	/**
	 * 根据上级ID获取子菜单
	 * @param parentId
	 * @param cache
	 * @return
	 */
	public List<SysMenu> getChildMenus(Long parentId, AuthResCache cache);
	
	
	
	
	/**
	 * 获取权限模块ID列表
	 * @param cache
	 * @return
	 */
	public Set<Long> getAuthModuIds(AuthResCache cache);
	
	

}
