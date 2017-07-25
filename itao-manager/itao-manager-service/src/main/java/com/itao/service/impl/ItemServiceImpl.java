package com.itao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itao.common.jedis.JedisClient;
import com.itao.common.pojo.EasyUIDataGridResult;
import com.itao.common.utils.E3Result;
import com.itao.common.utils.IDUtils;
import com.itao.common.utils.JsonUtils;
import com.itao.mapper.TbItemDescMapper;
import com.itao.mapper.TbItemMapper;
import com.itao.pojo.TbItem;
import com.itao.pojo.TbItemDesc;
import com.itao.pojo.TbItemExample;
import com.itao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private JedisClient jedisClient;
	@Resource
	private Destination topicDestination;
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	
	@Override
	public TbItem getItemById(Long id) {
		try {
			//从缓存中取
			String json = jedisClient.get(REDIS_ITEM_PRE + ":" + id + ":BASE");
			System.out.println("从缓存中取-getItemById");
			if(StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem item = itemMapper.selectByPrimaryKey(id);
		try {
			//将结果存入缓存
			jedisClient.set(REDIS_ITEM_PRE + ":" + id + ":BASE", JsonUtils.objectToJson(item));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_PRE + ":" + id + ":BASE", ITEM_CACHE_EXPIRE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		final long itemId=IDUtils.genItemId();
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
		
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//从缓存中取
		
		try {
			String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
			System.out.println("从缓存中取：getITemDescById");
			if(StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//把结果放在缓存里
		try {
			jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
		
	}

}
