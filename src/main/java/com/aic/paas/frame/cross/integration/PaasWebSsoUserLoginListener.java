package com.aic.paas.frame.cross.integration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.frame.cross.bean.CSysMenu;
import com.aic.paas.frame.cross.bean.CSysModu;
import com.aic.paas.frame.cross.bean.CSysOpOrg;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysOpOrg;
import com.aic.paas.frame.cross.bean.SysOrg;
import com.aic.paas.frame.cross.bean.WsMerchent;
import com.aic.paas.frame.cross.peer.SysFramePeer;
import com.aic.paas.frame.cross.rest.MerchentSvc;
import com.aic.paas.frame.cross.rest.SysOpOrgSvc;
import com.aic.paas.frame.cross.rest.SysOpRoleSvc;
import com.aic.paas.frame.cross.rest.SysOrgSvc;
import com.binary.framework.bean.User;
import com.binary.framework.exception.ServiceException;

public class PaasWebSsoUserLoginListener extends SimpleSsoUserLoginListener {

	
	@Autowired
	SysFramePeer sysFramePeer;
	
	@Autowired
	SysOpRoleSvc sysOpRoleSvc;
	
	
	@Autowired
	SysOrgSvc sysOrgSvc;
	
	@Autowired
	SysOpOrgSvc sysOpOrgSvc;
	
	@Autowired
	MerchentSvc merchentSvc;
	
	
	
	@Override
	public void onLoginSuccess(HttpServletRequest request, User user, String sessionId) {
		super.onLoginSuccess(request, user, sessionId);
		
		PaasWebSsoLoginUser u = user.owner(PaasWebSsoLoginUser.class);
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
		
		CSysOpOrg oporgcdt = new CSysOpOrg();
		oporgcdt.setOpId(u.getId());
		oporgcdt.setDirectFlag(1);
		oporgcdt.setStatus(1);
		List<SysOpOrg> orgopls = sysOpOrgSvc.queryListByOpId(u.getId(), oporgcdt, null);
		if(orgopls.size() == 0) throw new ServiceException(" not found direct org by user '"+u.getLoginCode()+"'! ");
		
		SysOpOrg oporg = orgopls.get(0);
		SysOrg org = sysOrgSvc.queryById(oporg.getOrgId());
		if(org == null) throw new ServiceException(" not found org by id '"+oporg.getOrgId()+"'! ");
		
		WsMerchent merchent = merchentSvc.queryById(org.getAuthOrgId());
		if(merchent == null) throw new ServiceException(" not found merchent by id '"+org.getAuthOrgId()+"'! ");
		
		u.setOrg(org);
		u.setMerchent(merchent);
	}
	

}
