package com.itao.content.service;

import java.util.List;

import com.itao.common.pojo.EasyUITreeNode;
import com.itao.common.utils.E3Result;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCatList(long parentId);
	
	E3Result addContent(long parentId,String name);
}
