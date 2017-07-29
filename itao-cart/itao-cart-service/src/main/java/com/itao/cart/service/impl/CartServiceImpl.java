package com.itao.cart.service.impl;

import java.util.List;

import com.itao.cart.service.CartService;
import com.itao.common.utils.E3Result;
import com.itao.pojo.TbItem;

public class CartServiceImpl implements CartService{

	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E3Result clearCartItem(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
