package com.itao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itao.common.jedis.JedisClient;
import com.itao.common.utils.E3Result;
import com.itao.common.utils.JsonUtils;
import com.itao.pojo.TbUser;
import com.itao.sso.service.TokenService;

import redis.clients.jedis.Jedis;
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result getUserByToken(String token) {
		String json = jedisClient.get("SESSION_EXPIRE:"+token);
		if (StringUtils.isBlank(token)) {
			
			return E3Result.build(201, "用户登录已经过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return E3Result.ok(user);
		
	}

}
