package com.aic.paas.frame.cross.peer;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aic.paas.frame.cross.bean.CSysMenu;
import com.aic.paas.frame.cross.bean.CSysModu;
import com.aic.paas.frame.cross.bean.CSysOp;
import com.aic.paas.frame.cross.bean.CSysOpRole;
import com.aic.paas.frame.cross.bean.CSysRole;
import com.aic.paas.frame.cross.bean.SysCode;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysModuInfo;
import com.aic.paas.frame.cross.bean.SysOp;
import com.aic.paas.frame.cross.bean.SysOpRole;
import com.aic.paas.frame.cross.bean.SysRole;

public interface SysFramePeer {

	
	
	
	/**
	 * 查询系统代码
	 * @param defCode : 代码定义名称
	 * @param codes : 指定代码
	 * @return
	 */
	public List<SysCode> getCodeList(String defCode, String[] codes);

	 
	
	/**
	 * 批量获取系统代码
	 * @param defCodes
	 * @return
	 */
	public Map<String, List<SysCode>> getCodeListBatch(String[] defCodes);
	
	
	
	
	/**
	 * 跟据模块ID获取模块相关信息
	 * @param moduId
	 * @return
	 */
	public SysModuInfo getModuInfoById(Long moduId);

	
	
	/**
	 * 跟据模块代码获取模块相关信息
	 * @param moduId
	 * @return
	 */
	public SysModuInfo getModuInfoByCode(String moduCode);
	
	
	
	/**
	 * 不分页查询模块
	 * @param cdt : 条件对象
	 * @param orders : 排序字段, 多字段以逗号分隔
	 * @return 系统模块表[SYS_MODU]查询列表
	 */
	public List<SysModu> getModuList(CSysModu cdt, String orders);

	
	/**
	 * 不分页查询菜单
	 * @param cdt : 条件对象
	 * @param orders : 排序字段, 多字段以逗号分隔
	 * @return 系统模块表[SYS_MODU]查询列表
	 */
	public List<SysMenu> getMenuList(CSysMenu cdt, String orders);
	
	
	
	
	
	/**
	 * 跟据ID查询用户
	 * @param loginCode
	 * @return
	 */
	public SysOp getOpById(Long opId);
	
	
	/**
	 * 跟据用户代码查询用户
	 * @param loginCode
	 * @return
	 */
	public SysOp getOpByCode(String opCode);
	
	
	/**
	 * 跟据登录代码查询用户
	 * @param loginCode
	 * @return
	 */
	public SysOp getOpByLoginCode(String loginCode);
	
	
	/**
	 * 跟据登录代码查询用户
	 * @param orders
	 * @return
	 */
	public List<SysOp> getOpList(CSysOp cdt, String orders);
	
	
	
	/**
	 * 更新用户登录密码
	 * @param opId : 用户ID
	 * @param oldPwd : 旧密码
	 * @param newPwd : 新密码
	 */
	public Integer updateOpPwd(Long opId, String oldPwd, String newPwd);
	
	
	
	/**
	 * 获取用户角色
	 * @param cdt
	 * @param orders
	 * @return
	 */
	public List<SysOpRole> getOpRoles(Long opId, CSysOpRole cdt, String orders);
	
	
	
	
	/**
	 * 获取角色列表
	 * @param cdt
	 * @param orders
	 * @return
	 */
	public List<SysRole> getRoleList(CSysRole cdt, String orders);
	
	
	
	/**
	 * 设置用户登录日志
	 * @param op
	 * @param authCode : 登录权限认证码
	 */
	public void setOpLoginLog(SysOp op, String authCode, String sessionId);
	
	
	
	/**
	 * 设置用户登出日志
	 * @param op
	 * @param authCode : 登录权限认证码
	 */
	public void setOpLogoutLog(Long opId, String sessionId);
	
	
	
	/**
	 * 获取指定用户下所有模块
	 * @param opId : 指定用户
	 * @param cdt : 指定查询条件
	 * @param orders : 排序
	 * @param beeauth : 是否带权限空制, 缺省为true
	 * @return
	 */
	public List<SysModu> getAllModus(Long opId, CSysModu cdt, String orders, Boolean beeauth);
	
	
	
	
	/**
	 * 获取指定用户下所有模块ID
	 * @param opId : 指定用户
	 * @param cdt : 指定查询条件
	 * @param orders : 排序
	 * @param beeauth : 是否带权限空制, 缺省为true
	 * @return
	 */
	public Long[] getAllModuIds(Long opId, CSysModu cdt, String orders, Boolean beeauth);
	
	
	
	
	
	/**
	 * 获取指定用户下所有菜单
	 * @param opId : 指定用户
	 * @param cdt : 指定查询条件
	 * @param orders : 排序
	 * @param beeauth : 是否带权限空制, 缺省为true
	 * @return
	 */
	public List<SysMenu> getAllMenus(Long opId, CSysMenu cdt, String orders, Boolean beeauth);
	
	
	
	
	
		
	/**
	 * 验证用户是否具有指定模块的权限
	 * @param opId : 用户ID
	 * @param moduId : 被验证的模块
	 * @return
	 */
	public boolean verifyModuId(Long opId, Long moduId);
	
	
	
	
	/**
	 * 验证用户是否具有指定模块的权限
	 * @param opId : 用户ID
	 * @param modu : 被验证的模块
	 * @return
	 */
	public boolean verifyModu(Long opId, SysModu modu);
	
	
	
	
	
	
	/**
	 * 用户登录后需要调用此方法
	 * @param request
	 */
	public void refreshLogin(HttpServletRequest request);
	
	
	
	/**
	 * 根据上级ID获取子菜单
	 * @param parentId
	 * @param cache
	 * @return
	 */
	public List<SysMenu> getChildMenus(HttpServletRequest request, Long parentId);
	
	
	/**
	 * 跟据模块ID跳转
	 * @param request
	 * @param moduId
	 * @return
	 */
	public String forwardModuleById(HttpServletRequest request, Long moduId);
	
	
	
	/**
	 * 跟据模块ID跳转
	 * @param request
	 * @param moduId
	 * @param dropCodes : 如果参数指定了dropCodes, 则补充至系统配置的列表中
	 * @param jss : 如果指定了JS, 补充至系统配置的列表中
	 * @return
	 */
	public String forwardModuleById(HttpServletRequest request, Long moduId, String[] dropCodes, String[] jss);
	
	
	
	/**
	 * 跟据模块代码跳转
	 * @param request
	 * @param moduCode
	 * @return
	 */
	public String forwardModuleByCode(HttpServletRequest request, String moduCode);
	
	
	
	/**
	 * 跟据模块代码跳转
	 * @param request
	 * @param moduCode
	 * @param dropCodes : 如果参数指定了dropCodes, 则补充至系统配置的列表中
	 * @param jss : 如果指定了JS, 补充至系统配置的列表中
	 * @return
	 */
	public String forwardModuleByCode(HttpServletRequest request, String moduCode, String[] dropCodes, String[] jss);
	
	
	
}
