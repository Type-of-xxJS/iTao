package com.itao.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.itao.pojo.TbOrder;
import com.itao.pojo.TbOrderItem;
import com.itao.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable {

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
