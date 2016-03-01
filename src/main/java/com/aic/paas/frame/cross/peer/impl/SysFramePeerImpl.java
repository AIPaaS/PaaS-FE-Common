package com.aic.paas.frame.cross.peer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.aic.paas.frame.cross.bean.CSysMenu;
import com.aic.paas.frame.cross.bean.CSysModu;
import com.aic.paas.frame.cross.bean.CSysOp;
import com.aic.paas.frame.cross.bean.CSysOpRole;
import com.aic.paas.frame.cross.bean.CSysRole;
import com.aic.paas.frame.cross.bean.DropCondition;
import com.aic.paas.frame.cross.bean.DropRecord;
import com.aic.paas.frame.cross.bean.ModuInfo;
import com.aic.paas.frame.cross.bean.RequestParameterMap;
import com.aic.paas.frame.cross.bean.SysCode;
import com.aic.paas.frame.cross.bean.SysCodeBatch;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysModuDrop;
import com.aic.paas.frame.cross.bean.SysModuInfo;
import com.aic.paas.frame.cross.bean.SysModuRes;
import com.aic.paas.frame.cross.bean.SysOp;
import com.aic.paas.frame.cross.bean.SysOpRole;
import com.aic.paas.frame.cross.bean.SysRole;
import com.aic.paas.frame.cross.peer.AuthResCache;
import com.aic.paas.frame.cross.peer.SessionAuthResCache;
import com.aic.paas.frame.cross.peer.SysAuthResPeer;
import com.aic.paas.frame.cross.peer.SysFramePeer;
import com.aic.paas.frame.cross.rest.SysFrameSvc;
import com.aic.paas.frame.util.ComponentUtil;
import com.aic.paas.frame.util.RequestKey;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ServiceException;

public class SysFramePeerImpl implements SysFramePeer {

	
	private final String SYS_FRAME_COMM_SESSION_AUTH_RES_CACHE_KEY = "SYS_FRAME_COMM_SESSION_AUTH_RES_CACHE_KEY";
		
	
	
	@Autowired
	SysAuthResPeer sysAuthResPeer;
	
	
	@Autowired
	SysFrameSvc sysFrameSvc;
	
	
	
	@Override
	public List<SysCode> getCodeList(String defCode, String[] codes) {
		return sysFrameSvc.getCodeList(defCode, codes);
	}

	@Override
	public Map<String, List<SysCode>> getCodeListBatch(String[] defCodes) {
		List<SysCodeBatch> ls = sysFrameSvc.getCodeListBatch(defCodes);
		Map<String, List<SysCode>> map = new HashMap<String, List<SysCode>>();
		if(ls!=null && ls.size()>0) {
			for(int i=0; i<ls.size(); i++) {
				SysCodeBatch cb = ls.get(i);
				map.put(cb.getViewName(), cb.getCodeList());
			}
		}
		return map;
	}
	
	@Override
	public SysModuInfo getModuInfoById(Long moduId) {
		return sysFrameSvc.getModuInfoById(moduId);
	}

	
	@Override
	public SysModuInfo getModuInfoByCode(String moduCode) {
		return sysFrameSvc.getModuInfoByCode(moduCode);
	}
	
	
	@Override
	public List<SysModu> getModuList(CSysModu cdt, String orders) {
		return sysFrameSvc.getModuList(cdt, orders);
	}
	
	
	@Override
	public List<SysMenu> getMenuList(CSysMenu cdt, String orders) {
		return sysFrameSvc.getMenuList(cdt, orders);
	}
	
	@Override
	public SysOp getOpById(Long opId) {
		return sysFrameSvc.getOpById(opId);
	}
	
	@Override
	public SysOp getOpByCode(String opCode) {
		return sysFrameSvc.getOpByCode(opCode);
	}
	
	@Override
	public List<SysOp> getOpList(CSysOp cdt, String orders) {
		return sysFrameSvc.getOpList(cdt, orders);
	}
	
	
	@Override
	public Integer updateOpPwd(Long opId, String oldPwd, String newPwd) {
		return sysFrameSvc.updateOpPwd(opId, oldPwd, newPwd);
	}
	
	@Override
	public SysOp getOpByLoginCode(String loginCode) {
		return sysFrameSvc.getOpByLoginCode(loginCode);
	}
	
	
	@Override
	public List<SysOpRole> getOpRoles(Long opId, CSysOpRole cdt, String orders) {
		return sysFrameSvc.getOpRoles(opId, cdt, orders);
	}
	
	
	
	@Override
	public List<SysRole> getRoleList(CSysRole cdt, String orders) {
		return sysFrameSvc.getRoleList(cdt, orders);
	}
	
	
	@Override
	public void setOpLoginLog(SysOp op, String authCode, String sessionId) {
		sysFrameSvc.setOpLoginLog(op, authCode, sessionId);
	}
	
	
	@Override
	public void setOpLogoutLog(Long opId, String sessionId) {
		sysFrameSvc.setOpLogoutLog(opId, sessionId);
	}
	
	
	
	@Override
	public List<SysModu> getAllModus(Long opId, CSysModu cdt, String orders, Boolean beeauth) {
		return sysFrameSvc.getAllModus(opId, cdt, orders, beeauth);
	}
	
	
	@Override
	public Long[] getAllModuIds(Long opId, CSysModu cdt, String orders, Boolean beeauth) {
		return sysFrameSvc.getAllModuIds(opId, cdt, orders, beeauth);
	}
	
	
	@Override
	public List<SysMenu> getAllMenus(Long opId, CSysMenu cdt, String orders, Boolean beeauth) {
		return sysFrameSvc.getAllMenus(opId, cdt, orders, beeauth);
	}
	
	@Override
	public boolean verifyModuId(Long opId, Long moduId) {
		return sysFrameSvc.verifyModuId(opId, moduId);
	}
	
	@Override
	public boolean verifyModu(Long opId, SysModu modu) {
		return sysFrameSvc.verifyModu(opId, modu);
	}
	
	
	
	protected AuthResCache getAuthResCache(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) throw new ServiceException(" the user is not login! ");
		AuthResCache cache = (AuthResCache) session.getAttribute(SYS_FRAME_COMM_SESSION_AUTH_RES_CACHE_KEY);
		if(cache == null) throw new ServiceException(" not found auth-res-cache, Please check sysFrame.refreshLogin! ");
		return cache;
	}
	
	
	
	
	@Override
	public void refreshLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) throw new ServiceException(" the user is not login! ");
		AuthResCache cache = new SessionAuthResCache(session);
		session.setAttribute(SYS_FRAME_COMM_SESSION_AUTH_RES_CACHE_KEY, cache);
		sysAuthResPeer.initialization(cache);
	}
	
	
	@Override
	public List<SysMenu> getChildMenus(HttpServletRequest request, Long parentId) {
		AuthResCache cache = getAuthResCache(request);
		return sysAuthResPeer.getChildMenus(parentId, cache);
	}
	
	
	@Override
	public String forwardModuleById(HttpServletRequest request, Long moduId) {
		return forwardModuleById(request, moduId, null, null);
	}
	
	@Override
	public String forwardModuleById(HttpServletRequest request, Long moduId, String[] dropCodes, String[] jss) {
		BinaryUtils.checkEmpty(moduId, "moduId");
		SysModuInfo moduInfo = getModuInfoById(moduId);
		if(moduInfo == null) throw new ServiceException(" not found modu by id '"+moduId+"'! ");
		return forwardModule(request, moduInfo, dropCodes, jss);
	}
	
	
	@Override
	public String forwardModuleByCode(HttpServletRequest request, String moduCode) {
		return forwardModuleByCode(request, moduCode, null, null);
	}
	
	
	@Override
	public String forwardModuleByCode(HttpServletRequest request, String moduCode, String[] dropCodes, String[] jss) {
		BinaryUtils.checkEmpty(moduCode, "moduCode");
		SysModuInfo moduInfo = getModuInfoByCode(moduCode);
		if(moduInfo == null) throw new ServiceException(" not found modu by code '"+moduCode+"'! ");
		return forwardModule(request, moduInfo, dropCodes, jss);
	}
	
	
	
	
	
	protected String forwardModule(HttpServletRequest request, SysModuInfo moduInfo, String[] dropCodes, String[] jss) {
		SysModu modu = moduInfo.getModu();
		
		//验证权限
		AuthResCache cache = getAuthResCache(request);
		Set<Long> moduIds = sysAuthResPeer.getAuthModuIds(cache);
		if(!moduIds.contains(modu.getId())) {
			throw new ServiceException(" You do not have permission to access the module '["+modu.getModuCode()+"] "+modu.getModuName()+"'!  ");
		}
		
		String url = modu.getModuUrl();
		String param = modu.getModuParam();
		if(url==null || (url=url.trim()).length()==0) {
			url = "/frame/jsp/dynamic.jsp";
		}
		
		if(param!=null && (param=param.trim()).length()>0) {
			char first = param.charAt(0);
			if(first != '&') param = "&" + param;
			url += "?1=1" + param;
		}
		
		if(url.indexOf('$') > 0) {
			RequestParameterMap requestMap = new RequestParameterMap(request);
			url = BinaryUtils.parseExpression(url).link(requestMap, true, null).toString();
		}
		
		if(url.startsWith("http://")) {
			return "redirect:" + url;
		}else {
			ModuInfo info = toModuInfo(request, moduInfo, dropCodes, jss);
			request.setAttribute(RequestKey.DYNAMIC_MODUINFO_KEY, info);
			
			char first = url.charAt(0);
			if(first != '/') url = "/" + url;
			return "forward:" + url;
		}
	}
	
	
	
	protected ModuInfo toModuInfo(HttpServletRequest request, SysModuInfo moduInfo, String[] dropCodes, String[] jss) {
		SysModu modu = moduInfo.getModu();
		List<SysModuDrop> moduDorpList = moduInfo.getModuDropList();
		List<SysModuRes> moduResList = moduInfo.getModuResList();
		if(moduResList == null) moduResList = new ArrayList<SysModuRes>();
		
		
		//获取模块所需要的代码数据
		Map<String, List<DropRecord>> dropMap = null;
		
		if(moduDorpList!=null && moduDorpList.size()>0) {
			List<DropCondition> dropCdtList = new ArrayList<DropCondition>();
			for(int i=0; i<moduDorpList.size(); i++) {
				SysModuDrop drop = moduDorpList.get(i);
				Integer addattrs = drop.getAddAttrs();
				Integer addempty = drop.getAddEmpty();
				dropCdtList.add(new DropCondition(drop.getDefCode(), addattrs!=null&&addattrs.intValue()==1, addempty!=null&&addempty.intValue()==1));
			}
			dropMap = ComponentUtil.getDropListBatch(dropCdtList);
		}
		
		
		if(dropMap == null) dropMap = new HashMap<String, List<DropRecord>>();
				
		//如果指定了dropCode
		if(dropCodes!=null && dropCodes.length>0) {
			List<String> codes = new ArrayList<String>();
			for(int i=0; i<dropCodes.length; i++) {
				String code = dropCodes[i].trim();
				if(!dropMap.containsKey(code)) {
					codes.add(code);
				}
			}
			if(codes.size() > 0) {
				Map<String, List<DropRecord>> map = ComponentUtil.getDropListBatch(dropCodes, true, true);
				if(map.size() > 0) dropMap.putAll(map);
			}
		}
		
		
		//如果指定了JS
		if(jss!=null && jss.length>0) {
			for(int i=0; i<jss.length; i++) {
				SysModuRes rs = new SysModuRes();
				rs.setResType(1);
				rs.setResUrl(jss[i]);
				moduResList.add(rs);
			}
		}
				
		
		//创建模块综合信息对象
		ModuInfo info = new ModuInfo();
		info.setModu(modu);
		info.setDropMap(dropMap);
		info.setModuResList(moduResList);
		
		return info;
	}



	
	
	
}
