package com.aic.paas.frame.cross.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexMvc {

	
	@RequestMapping("/")
	public String index() {
		return "redirect:/index.jsp";
	}
	
	
}
