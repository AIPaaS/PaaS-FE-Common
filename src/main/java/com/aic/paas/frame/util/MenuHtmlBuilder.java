package com.aic.paas.frame.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aic.paas.frame.cross.bean.SysMenu;
import com.aic.paas.frame.cross.bean.SysMenuNode;
import com.aic.paas.frame.cross.bean.SysModu;
import com.aic.paas.frame.cross.bean.SysModuNode;
import com.aic.paas.frame.cross.integration.AuthorityPreview;

public abstract class MenuHtmlBuilder {
	
	
	
	
	/**
	 * 构建菜单树Html
	 * @param contextPath
	 * @param nodes
	 * @param selModu
	 * @param preview
	 * @return
	 */
	public static String buildMenuTreeHtml(String contextPath, List<SysMenuNode> nodes, SysModu selModu, AuthorityPreview preview) {
		SysMenuNode rltMenuNode = preview.getMenuNodeByModuId(selModu.getId());
		if(rltMenuNode == null) return "";
		
		List<SysMenuNode> parentNodes = preview.getMenuLevelParentNodes(rltMenuNode.getMenu().getId());
		Set<Long> parentMenuIds = new HashSet<Long>();
		for(int i=0; i<parentNodes.size(); i++) {
			parentMenuIds.add(parentNodes.get(i).getMenu().getId());
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<nodes.size(); i++) {
			SysMenuNode node = nodes.get(i);
			buildMenuTreeHtml(sb, contextPath, node, parentMenuIds);
		}
		return sb.toString();
	}
	
	
	private static void buildMenuTreeHtml(StringBuilder sb, String contextPath, SysMenuNode node, Set<Long> selMenuIds) {
		List<SysMenuNode> childs = node.getChildren();
		boolean top = node.getParent() == null;
		boolean leaf = childs == null;
		SysMenu menu = node.getMenu();
		boolean active = selMenuIds.contains(menu.getId());
		
		sb.append("<li ").append(active?(top&&leaf?"class=\"active\"":(leaf?"":"class=\"open active\"")):"").append(">");
		if(leaf) {
			sb.append("<a ").append(active?"class=\"active\"":"").append(" href=\"").append(contextPath).append("/dispatch/mi/").append(menu.getModuId()).append("\">");
		}else {
			sb.append("<a href=\"###\" class=\"dropdown-toggle\">");
		}
		String icon = menu.getMenuImg();
		if(icon == null) icon = "";
		sb.append("<i class=\"fa ").append(icon).append("\">&nbsp;&nbsp;&nbsp;&nbsp;</i>")
			.append("<span>").append(menu.getMenuName()).append("</span>");
		if(!leaf) {
			sb.append("<i class=\"fa fa-chevron-circle-right drop-icon\"></i>");
		}
		sb.append("</a>");
		
		if(!leaf) {
			sb.append("<ul class=\"submenu\">");
			for(int i=0; i<childs.size(); i++) {
				SysMenuNode n = childs.get(i);
				buildMenuTreeHtml(sb, contextPath, n, selMenuIds);
			}
			sb.append("</ul>");
		}
		sb.append("</li>");
	}
	
	
	
	
	/**
	 * 构建面包线Html
	 * @param contextPath
	 * @param selModu
	 * @param preview
	 * @return
	 */
	public static String buildBreadLineHtml(String contextPath, SysModu selModu, AuthorityPreview preview) {
		List<SysModuNode> nodes = preview.getModuLevelParentNodes(selModu.getId());
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div id=\"BS_BREAD_LINE\" class=\"row\">")
			.append("<div class=\"col-lg-12\">")
			.append("<ol class=\"breadcrumb\">");
		
		for(int i=nodes.size()-1; i>=0; i--) {
			SysModuNode n = nodes.get(i);
			SysModu modu = n.getModu();
			int isdir = modu.getIsDir();
			
			sb.append("<li ").append(i==0?"class=\"active\"":"").append(">")
				.append("<span>");
			
			if(isdir==1 || i==0) {
				sb.append(modu.getModuName());
			}else {
				sb.append("<a id=\"BS_BREAD_LINE_LINK_"+modu.getId()+"_"+modu.getModuCode()+"\" href=\"###\">").append(modu.getModuName()).append("</a>");
			}
			
			sb.append("</span>")
				.append("</li>");
		}
		
		sb.append("</ol>")
			.append("</div>")
			.append("</div>");
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	

}



