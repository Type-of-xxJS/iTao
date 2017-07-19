package com.itao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itao.common.pojo.EasyUIDataGridResult;
import com.itao.common.utils.E3Result;
import com.itao.common.utils.IDUtils;
import com.itao.mapper.TbItemDescMapper;
import com.itao.mapper.TbItemMapper;
import com.itao.pojo.TbItem;
import com.itao.pojo.TbItemDesc;
import com.itao.pojo.TbItemExample;
import com.itao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	public TbItemMapper itemMapper;
	
	@Autowired
	public TbItemDescMapper itemDescMapper;
	
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

	@Override
	public E3Result addItem(TbItem item,String desc){
		
		//生产商品Id
		long itemId=IDUtils.genItemId();
		item.setId(itemId);
		//1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		
		//Desc
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		
		return E3Result.ok();
	}

}
