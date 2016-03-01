package com.aic.paas.frame.util;

import com.aic.paas.frame.cross.bean.TreeNode;


/**
 * ComponentUtil.toTreeNodeList执行迭代时所处理
 * @author wanwb
 */
public interface TreeNodeHandler<E> {

	
	
	
	/**
	 * 树节点处理之后事件
	 * @param node : 树节点对象
	 * @param record : 数据记录
	 */
	public void handle(TreeNode node, E record);
	
	
	
	
}
