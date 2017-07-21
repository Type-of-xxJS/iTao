package com.itao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.Log4jNestedDiagnosticContextFilter;

import com.itao.content.service.ContentService;
import com.itao.pojo.TbContent;

@Controller
public class IndexController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_lUNBO_ID;
	
	@Autowired
	private ContentService contentService;
	@RequestMapping("/index")
	
	public String showIndex(Model model) {
		
		List<TbContent> ad1List = contentService.getCotentListByCid(CONTENT_lUNBO_ID);
		model.addAttribute("ad1List",ad1List);
		
		return "index";
	}
}
