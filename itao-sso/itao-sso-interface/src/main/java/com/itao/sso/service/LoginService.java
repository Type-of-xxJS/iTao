package com.itao.sso.service;

import com.itao.common.utils.E3Result;
/**
 * 1.判断用户名密码是否正确
 * 2.登录成功模拟sessionId
 * 3.redis形式存储：key:token,value:user
 * @version 1.0
 */
public interface LoginService {
	E3Result userLogin(String userName,String password);
}
