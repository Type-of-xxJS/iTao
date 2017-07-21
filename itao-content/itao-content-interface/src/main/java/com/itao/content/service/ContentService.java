package com.itao.content.service;

import java.util.List;

import com.itao.common.utils.E3Result;
import com.itao.pojo.TbContent;

public interface ContentService {
	E3Result addContent(TbContent content);
	List<TbContent> getCotentListByCid(long cid);
}
