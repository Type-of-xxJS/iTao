package com.itao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itao.common.utils.E3Result;
import com.itao.search.service.SearchItemService;

@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList(){
		
		E3Result result = searchItemService.importAllItems();
		return result;
	}
}
