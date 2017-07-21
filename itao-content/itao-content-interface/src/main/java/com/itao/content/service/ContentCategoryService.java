package com.itao.content.service;

import java.util.List;

import com.itao.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(long parentId);
}
