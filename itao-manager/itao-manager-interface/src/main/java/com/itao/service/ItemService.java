package com.itao.service;

import com.itao.common.pojo.EasyUIDataGridResult;
import com.itao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long id);
    EasyUIDataGridResult getItemList(int page, int rows);
}
