package com.sun.shoppingmall.entity;

import java.util.List;

public class OrderListEntity {

	/*{
    "id": "00000001456",
    "pay_type": "1",
    "shop_id": "3",
    "user_id": "31",
    "grade": "0",
    "evaluate": "",
    "creation_time": "2016-11-09 17:29:38",
    "fetch_time": "0000-00-00 00:00:00",
    "state": "1",
    "pay_state": "2",
    "pay_time": "0000-00-00 00:00:00",
    "total_price": "11772.00",
    "product_id": "",
    "cancel_time": "0000-00-00 00:00:00",
    "sub_time": "0000-00-00 00:00:00",
    "coupon_price": "0.00",
    "evaluate_time": "0000-00-00 00:00:00",
    "contract_id": "0",
    "alipay_id": "201611094011",
    "trade_no": "",
    "wxpay_id": "",
    "ylpay_id": "",
    "shop_mes": "",
    "mobile": "",
    "shop_name": "济南电信洪楼广场店"
}*/
	
	private String id,pay_type,shop_id,creation_time,total_price,state,shop_name,total_num,state_name;
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	private List<OrderDEntity> listdetail;
	public List<OrderDEntity> getListdetail() {
		return listdetail;
	}

	public void setListdetail(List<OrderDEntity> listdetail) {
		this.listdetail = listdetail;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(String creation_time) {
		this.creation_time = creation_time;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
}
