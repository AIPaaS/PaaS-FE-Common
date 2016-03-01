package com.aic.paas.frame.cross.integration;

import com.aic.paas.frame.cross.bean.SysFrameUser;

public class PaasSsoLoginUser extends SysFrameUser {
	private static final long serialVersionUID = 1L;

	
	/** 权限视图 **/
	private AuthorityPreview authorityPreview;

	
	
	

	public AuthorityPreview getAuthorityPreview() {
		return authorityPreview;
	}


	public void setAuthorityPreview(AuthorityPreview authorityPreview) {
		this.authorityPreview = authorityPreview;
	}
	
	
	
	
	
	


	
	
	
}
