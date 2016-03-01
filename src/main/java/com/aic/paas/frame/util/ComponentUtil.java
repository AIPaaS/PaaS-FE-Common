package com.aic.paas.frame.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aic.paas.frame.cross.bean.DropCondition;
import com.aic.paas.frame.cross.bean.DropRecord;
import com.aic.paas.frame.cross.bean.NodeProMapping;
import com.aic.paas.frame.cross.bean.SysCode;
import com.aic.paas.frame.cross.bean.TreeNode;
import com.aic.paas.frame.cross.bean.TreeParams;
import com.binary.core.bean.BMProxy;
import com.binary.core.bean.Bean;
import com.binary.core.bean.BeanManager;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.exception.ServiceException;
import com.binary.jdbc.Page;


/**
 * 组件相关工具方法
 * @author wanwb
 */
public abstract class ComponentUtil {
	
	
	
	/**
	 * 对下拉列表数据添加空行
	 * @param records : 下拉列表数据
	 * @param index : 插入位置, 为空表示添加至最后
	 */
	public static void addEmptyDropRecord(List<DropRecord> records, Integer index) {
		DropRecord empty = new DropRecord("", "&nbsp;", null);
		if(index == null) {
			records.add(empty);
		}else {
			records.add(index, empty);
		}
	}
	
	
	
	/**
	 * 将数据转换成drop-list
	 * @param data : 数据
	 * @param codeField : 指定代码字段对应数据对象中哪个字段
	 * @param nameField : 指定名称字段对应数据对象中哪个字段
	 * @param appAttributes : 是否将记录作为Record.attributes属性
	 * @param addEmpty : 是否添加空行
	 * @return
	 */
	public static List<DropRecord> toDropList(List<?> data, String codeField, String nameField, Boolean appAttributes, Boolean addEmpty) {
		BinaryUtils.checkEmpty(codeField, "codeField");
		BinaryUtils.checkEmpty(nameField, "nameField");
		
		List<DropRecord> records = new ArrayList<DropRecord>();
		if(Boolean.TRUE.equals(addEmpty)) addEmptyDropRecord(records, null);
		
		if(data!=null && data.size()>0) {
			BMProxy<Object> proxy = null;
			boolean appattr = Boolean.TRUE.equals(appAttributes);
			
			for(int i=0; i<data.size(); i++) {
				Object obj = data.get(i);
				if(obj == null) throw new ServiceException(" the data["+i+"] is null! ");
				
				if(i == 0) {
					proxy = BMProxy.getInstance(obj);
				}else {
					proxy.replaceInnerObject(obj);
				}
				
				String code = Conver.to(proxy.get(codeField), String.class);
				String name = Conver.to(proxy.get(nameField), String.class);
				
				if(code==null || code.length()==0) throw new ServiceException(" the code-value is empty in data["+i+"]."+codeField+"! ");
				if(name==null || name.length()==0) throw new ServiceException(" the name-value is empty in data["+i+"]."+nameField+"! ");
				
				records.add(new DropRecord(code, name, appattr?obj:null));
			}
		}
		
		return records;
	}
	
	
	
	
	
	/**
	 * 将普通数据转换成树节点数据
	 * @param data : 数据
	 * @param mappings : 树节点映射说明
	 * @param appAttributes : 是否将记录作为Node.attributes属性
	 * @return
	 */
	public static List<TreeNode> toTreeNodeList(List<?> data, Boolean appAttributes, Boolean appCheckbox, NodeProMapping[] mappings) {
		return toTreeNodeList(data, appAttributes, appCheckbox, mappings, null);
	}
	
	
	
	
	/**
	 * 将普通数据转换成树节点数据
	 * @param data : 数据
	 * @param mappings : 树节点映射说明
	 * @param appAttributes : 是否将记录作为Node.attributes属性
	 * @return
	 */
	public static <T> List<TreeNode> toTreeNodeList(List<T> data, Boolean appAttributes, Boolean appCheckbox, NodeProMapping[] mappings, TreeNodeHandler<T> handler) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		
		if(data!=null && data.size()>0) {
			BinaryUtils.checkEmpty(mappings, true, "mappings");
			BMProxy<T> dataproxy = null;
			BMProxy<TreeNode> nodeproxy = BMProxy.getInstance(TreeNode.class);
			
			boolean appattr = Boolean.TRUE.equals(appAttributes);
			boolean checkbox = Boolean.TRUE.equals(appCheckbox);
			String contextPath = Local.getCriticalObject().getContextPath();
			String treeIconDir = "/frame/images/icons/16x18";
			
			for(int i=0; i<data.size(); i++) {
				T obj = data.get(i);
				if(obj == null) throw new ServiceException(" the data["+i+"] is null! ");
				
				if(i == 0) {
					dataproxy = BMProxy.getInstance(obj);
				}else {
					dataproxy.replaceInnerObject(obj);
				}
				
				TreeNode node = new TreeNode();
				if(checkbox) node.setChecked(false);
				if(appattr) node.setAttributes(obj);
				nodes.add(node);
				nodeproxy.replaceInnerObject(node);
				
				for(int j=0; j<mappings.length; j++) {
					NodeProMapping m = mappings[j];
					String nodeField = m.getNodeField();
					String dataField = m.getDataField();
					Object defaultValue = m.getDefaultValue();
					
					Object value = dataField!=null&&dataField.length()>0 ? dataproxy.get(dataField) : null;
					if(value==null && defaultValue!=null) {
						value = defaultValue;
					}
					
					nodeproxy.set(nodeField, value);
				}
				
				String icon = node.getIcon();
				if(icon!=null && (icon=icon.trim()).length()>0) {
					if(icon.charAt(0) != '/') icon = "/" + icon;
					node.setIcon(contextPath + treeIconDir + icon);
				}
				
				if(handler != null) {
					handler.handle(node, obj);
				}
			}
		}
		
		return nodes;
	}
	
	
	
	private static void fillNodeColumnProperties(List<TreeNode> nodes) {
		for(int i=0; i<nodes.size(); i++) {
			 TreeNode node = nodes.get(i);
			 String uiProvider = node.getUiProvider();
			 if(uiProvider==null || uiProvider.length()==0) node.setUiProvider("col");
		 }
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<Map<String,Object>> parseColumnTreeNodeList(List<TreeNode> nodes) {
		List<Map<String,Object>> processList = new ArrayList<Map<String,Object>>();
		
		Bean<TreeNode> nodebean = BeanManager.getBean(TreeNode.class);
		BMProxy<Object> proxy = null;
		
		for(int i=0; i<nodes.size(); i++) {
			TreeNode node = nodes.get(i);
			Object attributes = node.getAttributes();
			node.setAttributes(null);
			
			Map<String, Object> map = nodebean.toMap(node);
			if(attributes != null) {
				if(proxy == null) proxy = (BMProxy)BMProxy.getInstance(attributes.getClass());
				proxy.replaceInnerObject(attributes);
				proxy.copyTo(map);
			}
			
			processList.add(map);
		}
		
		return processList;
	}
	
	
	public static List<Map<String, Object>> toColumnTreeNodeList(List<TreeNode> nodes) {
		fillNodeColumnProperties(nodes);
		return parseColumnTreeNodeList(nodes);
	}
	public static List<Map<String, Object>> toColumnTreeNodePage(Page<TreeNode> page) {
		List<TreeNode> nodes = page.getData();
		fillNodeColumnProperties(nodes);
		List<Map<String,Object>> columnList = parseColumnTreeNodeList(nodes);
		
		if(columnList.size() > 0) {
			Map<String, Object> first = columnList.get(0);
			first.put("PageNum", page.getPageNum());
			first.put("PageSize", page.getPageSize());
			first.put("TotalPages", page.getTotalPages());
			first.put("TotalRows", page.getTotalRows());
		}
		return columnList;
	}
	public static List<Map<String, Object>> getEmptyColumnTreeNode(TreeParams tps) {
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		
		Long pageNum = tps.getPageNum();
		if(pageNum!=null && pageNum.intValue()==1) {
			Map<String, Object> first = new HashMap<String, Object>();
			first.put("PageNum", 1);
			first.put("PageSize", tps.getPageSize());
			first.put("TotalPages", 0);
			first.put("TotalRows", 0);
			columnList.add(first);
		}
		return columnList;
	}
	
	
	
	
	public static List<DropRecord> toDropList(List<SysCode> list, boolean appattr, boolean addempty) {
		List<DropRecord> records = new ArrayList<DropRecord>();
		if(addempty) ComponentUtil.addEmptyDropRecord(records, null);
		
		for(int i=0; i<list.size(); i++) {
			SysCode sc = list.get(i);
			String code = sc.getCode();
			String name = sc.getName();
			String alias = sc.getAlias();
			
			records.add(new DropRecord(code, BinaryUtils.isEmpty(alias)?name:alias, appattr?sc:null));
		}
		
		return records;
	}
	
	
	
	
	
	public static Map<String, List<DropRecord>> getDropListBatch(String[] defCodes, boolean appattr, boolean addempty) {
		BinaryUtils.checkEmpty(defCodes, "defCodes");
		
		Map<String, List<SysCode>> codesmap = SysFrameUtil.getCodeListBatch(defCodes);
		
		Map<String, List<DropRecord>> recordsmap = new HashMap<String, List<DropRecord>>();
		
		if(codesmap.size() > 0) {
			Iterator<Entry<String, List<SysCode>>> itor = codesmap.entrySet().iterator();
			while(itor.hasNext()) {
				Entry<String, List<SysCode>> e = itor.next();
				String defCode = e.getKey();
				List<SysCode> codes = e.getValue();
				List<DropRecord> records = ComponentUtil.toDropList(codes, appattr, addempty);
				recordsmap.put(defCode, records);
			}
		}
		
		return recordsmap;
	}
	
	
	public static Map<String, List<DropRecord>> getDropListBatch(List<DropCondition> cdtList) {
		String[] defCodes = new String[cdtList.size()];
		Map<String, DropCondition> cdtmap = new HashMap<String, DropCondition>();
		for(int i=0; i<cdtList.size(); i++) {
			DropCondition cdt = cdtList.get(i);
			defCodes[i] = cdt.getDefCode();
			cdtmap.put(defCodes[i].trim().toUpperCase(), cdt);
		}
		
		Map<String, List<SysCode>> codesmap = SysFrameUtil.getCodeListBatch(defCodes);
		Map<String, List<DropRecord>> recordsmap = new HashMap<String, List<DropRecord>>();
		
		if(codesmap.size() > 0) {
			Iterator<Entry<String, List<SysCode>>> itor = codesmap.entrySet().iterator();
			while(itor.hasNext()) {
				Entry<String, List<SysCode>> e = itor.next();
				String defCode = e.getKey();
				List<SysCode> codes = e.getValue();
				
				DropCondition cdt = cdtmap.get(defCode);
				boolean appattr = Boolean.TRUE.equals(cdt.getAddAttrs());
				boolean addempty = Boolean.TRUE.equals(cdt.getAddEmpty());
				List<DropRecord> records = ComponentUtil.toDropList(codes, appattr, addempty);
				recordsmap.put(defCode, records);
			}
		}
		
		return recordsmap;
	}
	
	

}
