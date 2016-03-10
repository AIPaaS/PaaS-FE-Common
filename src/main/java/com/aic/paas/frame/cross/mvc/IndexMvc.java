package com.aic.paas.frame.cross.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.core.http.URLResolver;
import com.binary.core.util.BinaryUtils;


@Controller
public class IndexMvc {
	private static Logger logger = LoggerFactory.getLogger(IndexMvc.class);

	
	@RequestMapping("/")
	public String index() {
		return "redirect:/index.jsp";
	}
	
	
	
	
	
	@RequestMapping("/refreshRelogin")
	public String refreshRelogin(HttpServletRequest request, HttpServletResponse response) {
		logger.info(" refresh relogin ... ");
		
		try {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
		}catch(Exception e) {
		}
		
		
		StringBuffer beforeUrl = request.getRequestURL();
		String qs = request.getQueryString();
		if(!BinaryUtils.isEmpty(qs)) {
			beforeUrl.append("?").append(qs);
		}
		
		String url = "/index.jsp?beforeUrl=" + URLResolver.encode(beforeUrl.toString());
		return "redirect:"+url;
	}
	
	
}
