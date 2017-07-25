package com.itao.service;

import com.itao.common.pojo.EasyUIDataGridResult;
import com.itao.common.utils.E3Result;
import com.itao.pojo.TbItem;
import com.itao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(Long id);
    EasyUIDataGridResult getItemList(int page, int rows);
    E3Result addItem(TbItem item,String desc);
    TbItemDesc getItemDescById(long itemId);
}
