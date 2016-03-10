package com.aic.paas.frame.cross.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.framework.util.ControllerUtils;


@Controller
public class IndexMvc {

	
	@RequestMapping("/")
	public String index() {
		return "redirect:/index.jsp";
	}
	
	
	
	
	
	@RequestMapping("/invalid")
	public void invalid(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.invalidate();
			}
		}catch(Exception e) {
		}
		ControllerUtils.returnJson(request, response, true);
	}
	
	
}
