package com.aic.paas.frame.cross.integration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.frame.cross.bean.CSysMenu;
import com.aic.paas.frame.cross.bean.CSysModu;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.peer.SysFramePeer;
import com.binary.framework.bean.User;

public class PaasSsoUserLoginListener extends SimpleSsoUserLoginListener {

	
	@Autowired
	SysFramePeer sysFramePeer;
	
	
	
	@Override
	public void onLoginSuccess(HttpServletRequest request, User user, String sessionId) {
		super.onLoginSuccess(request, user, sessionId);
		
		PaasSsoLoginUser u = user.owner(PaasSsoLoginUser.class);
		Long[] moduIds = u.getAuthModuIds();
		
		if(moduIds!=null && moduIds.length>0) {
			CSysMenu menucdt = new CSysMenu();
			menucdt.setModuIds(moduIds);
			List<SysMenu> menus = sysFramePeer.getMenuList(menucdt, " ORDER_NO, MENU_CODE, ID ");
			
			CSysModu moducdt = new CSysModu();
			moducdt.setIds(moduIds);
			List<SysModu> modus = sysFramePeer.getModuList(moducdt, " ORDER_NO, MODU_CODE, ID ");
			
			AuthorityPreview authorityPreview = new AuthorityPreview(menus, modus);
			u.setAuthorityPreview(authorityPreview);
		}
	}
	
	
	
	
	
}
