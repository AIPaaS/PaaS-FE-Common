package com.aic.paas.frame.cross.bean;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.binary.core.lang.ClassUtils;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.Condition;
import com.binary.json.JSON;

public class GridParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int pageSize=20;
	private long start;
	private boolean paging;
	
	private String targetOperate;		//group/sort  点击列头时的操作是分组还是排序
	
	private String sortAlias;			//排序别名
	private String groupAlias;			//分组别名
	private boolean sortType=true;		//true=asc	false=desc
	
	private String cdt;
	
	public GridParams() {
	}
	
	public GridParams(int start, int pageSize) {
		this.start = start;
		this.pageSize = pageSize;
		this.paging = true;
	}
	
	
	public void setInfo(Map<String, Object> map, String pageSize, String start) {
		this.setPageSize(pageSize);
		this.setStart(start);
		Object p = map.get("paging");
		this.setPaging(p==null?"false":p.toString());
		this.setTargetOperate((String)map.get("targetOperate"));
		this.setSortAlias((String)map.get("sortAlias"));
		this.setGroupAlias((String)map.get("groupAlias"));
		this.setSortType((String)map.get("sortType"));
	}
	
	
	public long getPageNum() {
		if(paging) {
			return start/pageSize+1;
		}else {
			return 1;
		}
	}
	
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize!=null && (pageSize=pageSize.trim()).length()>0 ? (int)Double.parseDouble(pageSize) : 20;
	}
	public void setStart(String start) {
		this.start = start!=null&&(start=start.trim()).length()>0 ? (int)Double.parseDouble(start) : 0;
	}
	public void setPaging(String paging) {
		this.paging = "true".equalsIgnoreCase(paging)||"1".equals(paging);
	}
	public void setSortType(String sortType) {
		this.sortType = !"desc".equalsIgnoreCase(sortType);
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isPaging() {
		return paging;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public String getTargetOperate() {
		return targetOperate;
	}
	public void setTargetOperate(String targetOperate) {
		this.targetOperate = targetOperate;
	}
	public String getSortAlias() {
		return sortAlias;
	}
	public void setSortAlias(String sortAlias) {
		this.sortAlias = sortAlias;
	}
	public boolean isSortType() {
		return sortType;
	}
	public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}

	public String getGroupAlias() {
		return groupAlias;
	}

	public void setGroupAlias(String groupAlias) {
		this.groupAlias = groupAlias;
	}
	
	
	
	public <T extends Condition> T getCondition(Class<T> clazz) {
		BinaryUtils.checkEmpty(clazz, "clazz");
		if(BinaryUtils.isEmpty(this.cdt)) {
			return ClassUtils.newInstance(clazz);
		}else {
			return JSON.toObject(this.cdt, clazz);
		}
	}
	
	
	public static GridParams valueOf(HttpServletRequest request) {
		String gpsjson = request.getParameter("gps");
		String cdt = request.getParameter("cdt");
		
		GridParams gps = null;
		if(BinaryUtils.isEmpty(gpsjson)) {
			gps = new GridParams();
		}else {
			gps = JSON.toObject(gpsjson, GridParams.class);
		}
		
		String pageSize = request.getParameter("pageSize");
		String start = request.getParameter("start");
		
		if(!BinaryUtils.isEmpty(pageSize)) {
			gps.pageSize = Integer.valueOf(pageSize);
		}
		if(!BinaryUtils.isEmpty(start)) {
			gps.start = Long.valueOf(start);
		}
		
		gps.cdt = cdt;
		
		return gps;
	}
	
	
	
	
	
	
	
}
