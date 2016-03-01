package com.aic.paas.frame.cross.integration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aic.paas.frame.util.SysFrameUtil;
import com.binary.framework.bean.User;
import com.binary.sso.client.auth.UserLoginListener;

public class SimpleSsoUserLoginListener implements UserLoginListener {
	private static Logger logger = LoggerFactory.getLogger(SimpleSsoUserLoginListener.class);
	
	
	
	@Override
	public void onLoginSuccess(HttpServletRequest request, User user, String sessionId) {
		logger.info(" sys-frame refresh login ...... ");
		SysFrameUtil.refreshLogin(request);
		logger.info(" sys-frame refresh login successful. ");
	}

}
