package com.itao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itao.common.pojo.EasyUITreeNode;
import com.itao.mapper.TbItemCatMapper;
import com.itao.pojo.TbItemCat;
import com.itao.pojo.TbItemCatExample;
import com.itao.pojo.TbItemCatExample.Criteria;
import com.itao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> resultList=new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}
		return resultList;
	}

}
