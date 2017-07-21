package com.itao.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itao.common.pojo.EasyUITreeNode;
import com.itao.content.service.ContentCategoryService;
import com.itao.mapper.TbItemCatMapper;
import com.itao.pojo.TbItemCat;
import com.itao.pojo.TbItemCatExample;
import com.itao.pojo.TbItemCatExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired 
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		
		//根据parentId获取节点下的内容
		TbItemCatExample itemCatExample=new TbItemCatExample();
		Criteria criteria = itemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = itemCatMapper.selectByExample(itemCatExample);
		//转换为EasyUITreeNodeList
		List<EasyUITreeNode> result=new ArrayList<>();
		for (TbItemCat itemCat : list) {
			
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent()?"closed":"open");
			
			result.add(node);
		}
		return result;
	}

}
