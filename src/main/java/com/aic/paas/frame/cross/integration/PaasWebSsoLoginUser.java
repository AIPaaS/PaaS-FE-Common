package com.aic.paas.frame.cross.integration;

import com.aic.paas.frame.cross.bean.SysOrg;
import com.aic.paas.frame.cross.bean.WsMerchent;

public class PaasWebSsoLoginUser extends PaasSsoLoginUser {
	private static final long serialVersionUID = 1L;

	
	
	/** 当前用户直属组织 **/
	private SysOrg org;
	
	
	/** 当前用户所属租户 **/
	private WsMerchent merchent;


	public SysOrg getOrg() {
		return org;
	}


	public void setOrg(SysOrg org) {
		this.org = org;
	}


	public WsMerchent getMerchent() {
		return merchent;
	}


	public void setMerchent(WsMerchent merchent) {
		this.merchent = merchent;
	}
	
	
	
	
	
}
