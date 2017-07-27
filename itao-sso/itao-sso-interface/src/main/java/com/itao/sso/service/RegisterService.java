package com.itao.sso.service;

import com.itao.common.utils.E3Result;
import com.itao.pojo.TbUser;

public interface RegisterService {
	E3Result register(TbUser user);
	E3Result checkData(String param,int type);
}
