package com.itao.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itao.common.pojo.EasyUIDataGridResult;
import com.itao.mapper.TbItemMapper;
import com.itao.pojo.TbItem;
import com.itao.pojo.TbItemExample;
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

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//設置分頁信息
		PageHelper.startPage(page, rows);
		//執行查詢
		TbItemExample itemExample=new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(itemExample);
		//取分頁信息
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;
	}

}
