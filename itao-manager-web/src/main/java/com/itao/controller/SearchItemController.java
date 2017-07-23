package com.itao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itao.common.pojo.SearchResult;
import com.itao.common.utils.E3Result;
import com.itao.search.service.SearchItemService;
import com.itao.search.service.SearchService;

@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList(){
		
		E3Result result = searchItemService.importAllItems();
		return result;
	}
	
	@RequestMapping("/index/item/search")
	@ResponseBody
	public SearchResult searchItemList() throws Exception{
		SearchResult result = searchService.search("手机", 1, 60);
		return result;
	}
}
