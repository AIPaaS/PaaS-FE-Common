package com.aic.paas.frame.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aic.paas.frame.cross.bean.CSysModu;
import com.aic.paas.frame.cross.bean.DropRecord;
import com.aic.paas.frame.cross.bean.ModuInfo;
import com.aic.paas.frame.cross.bean.SysCode;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysModuInfo;
import com.aic.paas.frame.cross.bean.SysModuRes;
import com.aic.paas.frame.cross.peer.SysFramePeer;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;

public class SysFrameUtil {
	
	
	
	private static SysFramePeer peer;
	

	
	public SysFrameUtil(SysFramePeer peer) {
		SysFrameUtil.peer = peer; 
	}
	
	
	private static SysFramePeer getPeer() {
		if(peer == null) {
			throw new ServiceException(" not init sysFramePeer in SysFrameUtil! ");
		}
		return peer;
	}
	
	
	
	/**
	 * 查询系统代码
	 * @param defCode : 代码定义名称
	 * @param codes : 指定代码
	 * @return
	 */
	public static List<SysCode> getCodeList(String defCode, String[] codes) {
		return getPeer().getCodeList(defCode, codes);
	}

	 
	
	/**
	 * 批量获取系统代码
	 * @param defCodes
	 * @return
	 */
	public static Map<String, List<SysCode>> getCodeListBatch(String[] defCodes) {
		return getPeer().getCodeListBatch(defCodes);
	}
	
	
	
	/**
	 * 跟据模块ID获取模块相关信息
	 * @param moduId
	 * @return
	 */
	public static SysModuInfo getModuInfoById(Long moduId) {
		return getPeer().getModuInfoById(moduId);
	}

	
	
	/**
	 * 跟据模块代码获取模块相关信息
	 * @param moduId
	 * @return
	 */
	public static SysModuInfo getModuInfoByCode(String moduCode) {
		return getPeer().getModuInfoByCode(moduCode);
	}
	
	
	
	
	/**
	 * 跟据模块ID获取模块
	 * @param moduId
	 * @return
	 */
	public static SysModu getModuById(Long moduId) {
		BinaryUtils.checkEmpty(moduId, "moduId");
		CSysModu cdt = new CSysModu();
		cdt.setId(moduId);
		List<SysModu> ls = getPeer().getModuList(cdt, null);
		return ls.size()>0 ? ls.get(0) : null;
	}

	
	
	/**
	 * 跟据模块代码获取模块
	 * @param moduId
	 * @return
	 */
	public static SysModu getModuByCode(String moduCode) {
		BinaryUtils.checkEmpty(moduCode, "moduCode");
		CSysModu cdt = new CSysModu();
		cdt.setModuCodeEqual(moduCode);
		List<SysModu> ls = getPeer().getModuList(cdt, null);
		return ls.size()>0 ? ls.get(0) : null;
	}
	
	
	
	/**
	 * 用户登录后需要调用此方法
	 * @param request
	 */
	public static void refreshLogin(HttpServletRequest request) {
		getPeer().refreshLogin(request);
	}
	
	
	
	/**
	 * 根据上级ID获取子菜单
	 * @param parentId
	 * @param cache
	 * @return
	 */
	public static List<SysMenu> getChildMenus(HttpServletRequest request, Long parentId) {
		return getPeer().getChildMenus(request, parentId);
	}
	
	
	/**
	 * 跟据模块ID跳转
	 * @param request
	 * @param moduId
	 * @return
	 */
	public static String forwardModuleById(HttpServletRequest request, Long moduId) {
		return getPeer().forwardModuleById(request, moduId);
	}
	
	
	
	/**
	 * 跟据模块ID跳转
	 * @param request
	 * @param moduId
	 * @param dropCodes : 如果参数指定了dropCodes, 则补充至系统配置的列表中
	 * @param jss : 如果指定了JS, 补充至系统配置的列表中
	 * @return
	 */
	public static String forwardModuleById(HttpServletRequest request, Long moduId, String[] dropCodes, String[] jss) {
		return getPeer().forwardModuleById(request, moduId, dropCodes, jss);
	}
	
	
	
	/**
	 * 跟据模块代码跳转
	 * @param request
	 * @param moduCode
	 * @return
	 */
	public static String forwardModuleByCode(HttpServletRequest request, String moduCode) {
		return getPeer().forwardModuleByCode(request, moduCode);
	}
	
	
	
	/**
	 * 跟据模块代码跳转
	 * @param request
	 * @param moduCode
	 * @param dropCodes : 如果参数指定了dropCodes, 则补充至系统配置的列表中
	 * @param jss : 如果指定了JS, 补充至系统配置的列表中
	 * @return
	 */
	public static String forwardModuleByCode(HttpServletRequest request, String moduCode, String[] dropCodes, String[] jss) {
		return getPeer().forwardModuleByCode(request, moduCode, dropCodes, jss);
	}
	
	
	
	
	
	
	/**
	 * 获取模块信息
	 * @param sysCodePeer : 指定SysCodePeer对象
	 * @param moduId : 指定模块ID
	 * @param moduName : 指定模块名称
	 * @param defCodes : 指定定义代码
	 * @param jss : 指定JS资源
	 * 
	 * @deprecated use the SysFrameUtil.forwardModuleById()
	 * 
	 * @return
	 */
	@Deprecated
	public static ModuInfo getModuInfo(Long moduId, String moduName, String[] defCodes, String[] jss) {		
		ModuInfo info = new ModuInfo();
		
		SysModu modu = new SysModu();
		modu.setId(moduId);
		modu.setModuCode(String.valueOf(moduId));
		modu.setModuName(moduName);
		modu.setTechnicType(1);
		info.setModu(modu);
		
		Map<String, List<DropRecord>> dropMap = null;
		if(defCodes!=null && defCodes.length>0) {
			dropMap = ComponentUtil.getDropListBatch(defCodes, true, true);
		}else {
			dropMap = new HashMap<String, List<DropRecord>>();
		}
		info.setDropMap(dropMap);
		
		List<SysModuRes> moduResList = new ArrayList<SysModuRes>();
		for(int i=0; i<jss.length; i++) {
			SysModuRes rs_1 = new SysModuRes();
			rs_1.setResType(1);
			rs_1.setResUrl(jss[i]);
			moduResList.add(rs_1);
		}
		info.setModuResList(moduResList);
		
		return info;
	}
	
	
	
	
		
	
	
}
