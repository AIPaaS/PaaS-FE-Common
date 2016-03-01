package com.aic.paas.frame.cross.bean;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.binary.core.util.BinaryUtils;
import com.binary.json.JSON;



public class TreeParams implements Serializable {
	private static final long serialVersionUID = -1388868203296549034L;

	/** 上级节点ID **/
	private String parentId;
	
	/** 上级节点中param1值 **/
	private String param1;
	
	/** 上级节点中param2值 **/
	private String param2;
	
	/** 上级节点中param3值 **/
	private String param3;
	
	/** 返回时是否需要携带记录一起返回 **/
	private Boolean appAttributes;
	
	/** 是否添加复选框 **/
	private Boolean checkbox;
	
	
	/** 分页查询时需指定的页码 **/
	private Long pageNum;
	
	/** 分页查询时需指定的页大小 **/
	private Long pageSize;
	
	
	
	

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public Long getPageNum() {
		return pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getAppAttributes() {
		return appAttributes;
	}

	public void setAppAttributes(Boolean appAttributes) {
		this.appAttributes = appAttributes;
	}

	public Boolean getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}	
	
	
	
	public static TreeParams valueOf(HttpServletRequest request) {
		String jsonstring = request.getParameter("tps");
		
		if(BinaryUtils.isEmpty(jsonstring)) {
			return new TreeParams();
		}else {
			return JSON.toObject(jsonstring, TreeParams.class);
		}
	}
	
	
	
	
}
