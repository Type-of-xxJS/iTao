package com.itao.service.impl;

import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itao.mapper.TbItemMapper;
import com.itao.pojo.TbItem;
import com.itao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	public TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(Long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

}
