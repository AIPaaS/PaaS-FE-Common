package com.aic.paas.frame.cross.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 模块信息
 * @author wanwb
 */
public class ModuInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** 模块对象 **/
	private SysModu modu;
	
	
	/** 模块资源 **/
	private List<SysModuRes> moduResList;

	
	/** 模块所需要的代码表 **/
	private Map<String, List<DropRecord>> dropMap;
	
	

	public SysModu getModu() {
		return modu;
	}


	public void setModu(SysModu modu) {
		this.modu = modu;
	}


	public List<SysModuRes> getModuResList() {
		return moduResList;
	}


	public void setModuResList(List<SysModuRes> moduResList) {
		this.moduResList = moduResList;
	}


	public Map<String, List<DropRecord>> getDropMap() {
		return dropMap;
	}


	public void setDropMap(Map<String, List<DropRecord>> dropMap) {
		this.dropMap = dropMap;
	}


	
	
	
	
	
	

}
