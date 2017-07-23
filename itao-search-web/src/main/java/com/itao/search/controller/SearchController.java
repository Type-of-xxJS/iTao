package com.itao.search.controller;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.quartz.spi.InstanceIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itao.search.service.SearchService;

@Controller
public class SearchController {

	@Value("${SEARCH_RESULT_ROLWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String searchItem(String keyword,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception{
		
		//返回逻辑视图
		return "search";
	}
}
