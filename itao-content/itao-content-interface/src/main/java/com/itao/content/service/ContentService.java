package com.itao.content.service;

import java.util.List;

import com.itao.pojo.TbContent;

public interface ContentService {
	void addContent(TbContent content);
	List<TbContent> getCotentListByCid(long cid);
}
