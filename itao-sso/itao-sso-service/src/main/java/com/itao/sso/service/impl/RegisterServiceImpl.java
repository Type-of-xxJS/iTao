package com.itao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.itao.common.utils.E3Result;
import com.itao.mapper.TbUserMapper;
import com.itao.pojo.TbUser;
import com.itao.pojo.TbUserExample;
import com.itao.pojo.TbUserExample.Criteria;
import com.itao.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public E3Result register(TbUser user) {
		//数据有效性校验
		if (StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())
				|| StringUtils.isBlank(user.getPhone())) {
			return E3Result.build(400, "数据不完整，注册失败");
		}
		
		E3Result result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "此用户名已经被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean)result.getData()) {
			return E3Result.build(400, "手机号已经被占用");
		}
		//TODO:异常了
//		result = checkData(user.getEmail(), 3);
//		if (!(boolean)result.getData()) {
//			return E3Result.build(400, "邮箱已经被占用");
//		}
		
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//user.setEmail("aa@bb.com");
		//对密码md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		userMapper.insert(user);
		return E3Result.ok();
	}

	@Override
	public E3Result checkData(String param, int type) {
		
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1：用户名 2：手机号 3：邮箱
		if (type==1) {
			criteria.andUsernameEqualTo(param);
		}else if(type==2){
			criteria.andPhoneEqualTo(param);
		}else if(type==3){
			criteria.andEmailEqualTo(param);
		}else {
			return E3Result.build(400, "数据类型错误");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list!=null && list.size()>0) {
			//已经存在，返回false
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

}
