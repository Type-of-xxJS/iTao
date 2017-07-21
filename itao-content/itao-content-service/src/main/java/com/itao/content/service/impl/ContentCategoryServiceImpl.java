package com.itao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itao.common.pojo.EasyUITreeNode;
import com.itao.common.utils.E3Result;
import com.itao.content.service.ContentCategoryService;
import com.itao.mapper.TbContentCategoryMapper;
import com.itao.pojo.TbContentCategory;
import com.itao.pojo.TbContentCategoryExample;
import com.itao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired 
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		
		//根据parentId获取节点下的内容
		TbContentCategoryExample contentCategoryExample=new TbContentCategoryExample();
		Criteria criteria = contentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(contentCategoryExample);
		//转换为EasyUITreeNodeList
		List<EasyUITreeNode> result=new ArrayList<>();
		for (TbContentCategory contentCategory : list) {
			
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			
			result.add(node);
		}
		return result;
	}

	@Override
	public E3Result addContent(long parentId, String name) {
		
		TbContentCategory contentCategory=new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setIsParent(false);
		//1(正常),2(删除)
		contentCategory.setStatus(1);
		//默认排序
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		
		contentCategoryMapper.insert(contentCategory);
		
		//判断父节点的IsParent属性
		
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			
			parentNode.setIsParent(true);
			
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		
		return E3Result.ok(contentCategory);

	}

}
