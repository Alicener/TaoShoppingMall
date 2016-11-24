package com.sun.shoppingmall.entity;

public class TicketEntity {

/*	{
	    "id": "129",
	    "user_id": "2",
	    "coupon_id": "155",
	    "code": "10299856971157604191",
	    "use_time": "1473237162",
	    "get_time": "1473304938",
	    "state": "3",
	    "use_shop_id": "0",
	    "order_id": "1418",
	    "title": "11",
	    "img": "14732371708415.jpg",
	    "shop_id": "2",
	    "goods_id": "12558",
	    "money": "0.01",
	    "shop_name": "兰欣商贸城",
	    "start_time": "1473237167",
	    "end_time": "1573247969",
	    "out_time": "1573244364"
	}*/
	
	private String id,coupon_id,code,start_time,end_time,shop_name,money,goods_id,img,order_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
}
