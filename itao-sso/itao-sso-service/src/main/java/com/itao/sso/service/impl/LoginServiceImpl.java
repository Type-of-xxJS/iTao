package com.itao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.itao.common.jedis.JedisClient;
import com.itao.common.utils.E3Result;
import com.itao.common.utils.JsonUtils;
import com.itao.mapper.TbUserMapper;
import com.itao.pojo.TbUser;
import com.itao.pojo.TbUserExample;
import com.itao.pojo.TbUserExample.Criteria;
import com.itao.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result userLogin(String userName, String password) {
	
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		
		List<TbUser> list = userMapper.selectByExample(example);
		if (list==null || list.size()==0) {
			return E3Result.build(400, "用户名或密码错误");
		}
		
		TbUser user=list.get(0);
		//判断密码是否正确
		if (!(DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword()))) {
			return E3Result.build(400, "用户名或密码错误");
		}
		//如果正确，生成token
		//模拟sessionId
		String token=UUID.randomUUID().toString();
		user.setPassword(null);
		//交给统一的redis来管理
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		//把token返回
		return E3Result.ok(token);
	}

}
