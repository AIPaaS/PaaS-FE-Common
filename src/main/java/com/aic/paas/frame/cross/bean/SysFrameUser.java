package com.aic.paas.frame.cross.bean;

import com.binary.framework.bean.SimpleUser;

public class SysFrameUser extends SimpleUser {
	private static final long serialVersionUID = 1L;
	
	
	/** 是否权限管理 **/
	private Boolean openAuthMgr;
	
	
	
	/** 用户权限下的模块ID列表 **/
	private Long[] authModuIds;

	


	public Boolean getOpenAuthMgr() {
		return openAuthMgr;
	}



	public void setOpenAuthMgr(Boolean openAuthMgr) {
		this.openAuthMgr = openAuthMgr;
	}



	public Long[] getAuthModuIds() {
		return authModuIds;
	}



	public void setAuthModuIds(Long[] authModuIds) {
		this.authModuIds = authModuIds;
	}


	
	
	
	
	
	

}
