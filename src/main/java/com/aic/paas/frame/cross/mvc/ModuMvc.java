package com.aic.paas.frame.cross.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aic.paas.frame.cross.bean.NodeProMapping;
import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.TreeNode;
import com.aic.paas.frame.cross.bean.TreeParams;
import com.aic.paas.frame.util.ComponentUtil;
import com.aic.paas.frame.util.SysFrameUtil;
import com.aic.paas.frame.util.TreeNodeHandler;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.util.ControllerUtils;




/**
 * 系统资源相前服务
 * @author wanwb
 */
@Controller
@RequestMapping("/sys/frame/cross/modu")
public class ModuMvc {
	
	
	
	/**
	 * 获取系统菜单树
	 * @param tp
	 * @return
	 */
	@RequestMapping("/getMenuTree")
	public void getMenuTree(HttpServletRequest request, HttpServletResponse response) {
		TreeParams tp = TreeParams.valueOf(request);
		
		Long parentId = 0l;
		if(!BinaryUtils.isEmpty(tp.getParentId())) {
			parentId = Long.valueOf(tp.getParentId());
		}
		
		List<SysMenu> menus = SysFrameUtil.getChildMenus(request, parentId);
		
		List<TreeNode> nodes = null;
		Boolean appAttr = tp.getAppAttributes();
		if(appAttr == null) appAttr = true;
		if(menus.size() > 0) {
			final String contextPath = Local.getCriticalObject().getContextPath();
			nodes = ComponentUtil.toTreeNodeList(menus, appAttr, tp.getCheckbox(), new NodeProMapping[] {
					new NodeProMapping("id", "id", null),
					new NodeProMapping("text", "menuName", null),
					new NodeProMapping("leaf", "isLeaf", null),
					new NodeProMapping("icon", "menuImg", null),
					new NodeProMapping("parentid", "parentId", null),
			}, new TreeNodeHandler<SysMenu>() {
				public void handle(TreeNode node, SysMenu record) {
					String img = record.getMenuImg();
					if(BinaryUtils.isEmpty(img)) {
						int dir = record.getIsDir();	//0=模块    1=目录
						if(dir == 1) {
							node.setIcon(contextPath + "/frame/images/tree/f18.gif");
						}else {
							node.setIcon(contextPath + "/frame/images/tree/com.gif");
						}
					}
				}
			});
		}else {
			nodes = new ArrayList<TreeNode>();
		}
		
		ControllerUtils.returnSimpleJson(request, response, nodes);
	}
	
	
	
	
	/**
	 * 跟据模块ID打开一个模块
	 * @param moduleId : 模块ID
	 * @return 
	 */
	@RequestMapping("/openModuleById")
	public String openModuleById(HttpServletRequest request, HttpServletResponse response, Long moduleId) {
		return SysFrameUtil.forwardModuleById(request, moduleId);
	}
	
	
	
	/**
	 * 跟据模块代码打开一个模块
	 * @param moduleCode : 模块代码
	 * @return
	 */
	@RequestMapping("/openModuleByCode")
	public String openModuleByCode(HttpServletRequest request, HttpServletResponse response, String moduleCode) {
		return SysFrameUtil.forwardModuleByCode(request, moduleCode);
	}
	
	
	
	
	
	
}
