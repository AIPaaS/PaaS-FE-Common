package com.aic.paas.frame.cross.mvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aic.paas.frame.cross.bean.DropCondition;
import com.aic.paas.frame.cross.bean.DropRecord;
import com.aic.paas.frame.cross.bean.SysCode;
import com.aic.paas.frame.util.ComponentUtil;
import com.aic.paas.frame.util.SysFrameUtil;
import com.binary.framework.util.ControllerUtils;



/**
 * 下拉列表相关方法
 * @author wanwb
 */
@Controller
@RequestMapping("/sys/frame/cross/drop")
public class DropListMvc {

	
	
	
	/**
	 * 获取代码列表
	 * @param defCode : 指定代码定义代码
	 * @param codes : 指定只查询哪些代码
	 * @param appAttributes : 是否将记录作为Record.attributes属性
	 * @param addEmpty : 是否添加空行
	 * @return
	 */
	@RequestMapping("/getDropList")
	public void getDropList(HttpServletRequest request, HttpServletResponse response,
							String defCode, String[] codes, Boolean appAttributes, Boolean addEmpty) {
		List<SysCode> list = SysFrameUtil.getCodeList(defCode, codes);
		boolean appattr = Boolean.TRUE.equals(appAttributes);
		boolean addempty = Boolean.TRUE.equals(addEmpty);
		List<DropRecord> dropList = ComponentUtil.toDropList(list, appattr, addempty);
		ControllerUtils.returnJson(request, response, dropList);
	}
	
	
	
	
	
	/**
	 * 批量查询出多套代码列表, 此方法不会验证代码定义是否存在, 如果不存在则在返回结果Map中也不会存在
	 * @param defCodes : : 指定代码定义代码列表
	 * @param appAttributes : 是否将记录作为Record.attributes属性
	 * @param addEmpty : 是否添加空行
	 * @return Map中的Key会全部大写
	 */
	@RequestMapping("/getDropListBatchSingle")
	public void getDropListBatchSingle(HttpServletRequest request, HttpServletResponse response,
												String[] defCodes, Boolean appAttributes, Boolean addEmpty) {
		Map<String, List<SysCode>> codesmap = SysFrameUtil.getCodeListBatch(defCodes);
		Map<String, List<DropRecord>> recordsmap = new HashMap<String, List<DropRecord>>();
		if(codesmap.size() > 0) {
			boolean appattr = Boolean.TRUE.equals(appAttributes);
			boolean addempty = Boolean.TRUE.equals(addEmpty);
			Iterator<Entry<String, List<SysCode>>> itor = codesmap.entrySet().iterator();
			while(itor.hasNext()) {
				Entry<String, List<SysCode>> e = itor.next();
				String defCode = e.getKey();
				List<SysCode> codes = e.getValue();
				List<DropRecord> records = ComponentUtil.toDropList(codes, appattr, addempty);
				recordsmap.put(defCode, records);
			}
		}
		ControllerUtils.returnJson(request, response, recordsmap);
	}
	
		
	
	
	
	/**
	 * 批量查询出多套代码列表, 此方法不会验证代码定义是否存在, 如果不存在则在返回结果Map中也不会存在
	 * @param cdtList : 条件列表
	 * @return Map中的Key会全部大写
	 */
	@RequestMapping("/getDropListBatch")
	public void getDropListBatch(HttpServletRequest request, HttpServletResponse response, List<DropCondition> cdtList) {
		Map<String, List<DropRecord>> recordsmap = ComponentUtil.getDropListBatch(cdtList);
		ControllerUtils.returnJson(request, response, recordsmap);
	}
	
	
	
	
	
	
	
}




