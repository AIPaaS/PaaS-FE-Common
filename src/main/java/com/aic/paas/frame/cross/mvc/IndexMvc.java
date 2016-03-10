package com.aic.paas.frame.cross.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.core.http.URLResolver;


@Controller
public class IndexMvc {
	private static Logger logger = LoggerFactory.getLogger(IndexMvc.class);

	
	@RequestMapping("/")
	public String index() {
		return "redirect:/index.jsp";
	}
	
	
	
	
	
	@RequestMapping("/refreshRelogin")
	public String refreshRelogin(HttpServletRequest request, HttpServletResponse response, String beforeUrl) {
		logger.info(" refresh relogin ... " + beforeUrl);
		
		try {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
		}catch(Exception e) {
		}
		
		String forward = "redirect:/index.jsp";
		if(beforeUrl != null) {
			if(beforeUrl.indexOf('%') > -1) beforeUrl = URLResolver.decode(beforeUrl);
			forward += "?beforeUrl=" + URLResolver.encode(beforeUrl);
		}
		
		return forward;
	}
	
	
}
