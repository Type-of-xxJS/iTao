package com.itao.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itao.content.service.ContentService;
import com.itao.mapper.TbContentMapper;
import com.itao.pojo.TbContent;
import com.itao.pojo.TbContentExample;
import com.itao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Override
	public void addContent(TbContent content) {
		
		contentMapper.insert(content);
	}

	@Override
	public List<TbContent> getCotentListByCid(long cid) {
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		
		List<TbContent> list = contentMapper.selectByExample(example);
		return list;
		
	}

}
