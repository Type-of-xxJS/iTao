package com.itao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itao.common.utils.E3Result;
import com.itao.content.service.ContentCategoryService;
import com.itao.content.service.ContentService;
import com.itao.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content){
		return contentService.addContent(content);
	}
}
