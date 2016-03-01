package com.aic.paas.frame.cross.mvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.binary.core.io.FileSystem;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;
import com.binary.framework.util.ControllerUtils;
import com.binary.framework.web.LocalSpace;



@Controller
@RequestMapping("/sys/frame/cross/tmpfile")
public class TmpFileMvc {
	
	
	
	
	/**
	 * 附件上传
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("formFile") MultipartFile formFile) throws IOException {
		BinaryUtils.checkEmpty(formFile, "formFile");
		
		if(formFile.getSize() == 0) throw new ServiceException(" the formFile is empty! ");
		
		String fileName = formFile.getOriginalFilename();
		String suffix = "";
		if(fileName.indexOf('.') > 0) {
			suffix = fileName.substring(fileName.lastIndexOf('.')+1).trim();
		}
		
		String tmpdirpath = LocalSpace.getTmpRelativePath();
		File tmpdir = LocalSpace.getSpace(tmpdirpath);
		String outputName = BinaryUtils.getUUID();
		if(suffix.length() > 0) outputName += "." + suffix;
		File output = new File(tmpdir, outputName);
		
		InputStream is = null;
		try {
			is = formFile.getInputStream();
			FileSystem.copy(is, output);
		}finally {
			if(is != null) is.close();
		}
		
		String path = tmpdirpath + "/" + outputName;
		ControllerUtils.returnJson(request, response, path);
	}
	
	
	
	

}
