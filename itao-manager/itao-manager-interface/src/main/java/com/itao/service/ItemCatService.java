package com.itao.service;

import java.util.List;

import com.itao.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getCatList(long parentId);
}
