package com.itao.sso.service;

import com.itao.common.utils.E3Result;

public interface TokenService {
	E3Result getUserByToken(String token);
}
