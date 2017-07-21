package com.itao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itao.common.jedis.JedisClient;
import com.itao.common.utils.E3Result;
import com.itao.common.utils.JsonUtils;
import com.itao.content.service.ContentService;
import com.itao.mapper.TbContentMapper;
import com.itao.pojo.TbContent;
import com.itao.pojo.TbContentExample;
import com.itao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Override
	public E3Result addContent(TbContent content) {
		
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//缓存同步
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		System.out.println("同步缓存");
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getCotentListByCid(long cid) {
		
		try {
			//如果缓存中有则直接返回
			String json=jedisClient.hget(CONTENT_LIST, cid+"");
			if (StringUtils.isNoneBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				System.out.println("从缓存中获取");
				return list;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果没有则查询数据库
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//把结果添加到缓存中去
		try {
			jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
			System.out.println("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

}
