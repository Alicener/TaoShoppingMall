package com.sun.shoppingmall.entity;

public class ConcernEntity {
	
	/*{
	    "id": "10",
	    "title": "特百惠（Tupperware）乐活随心壶  750ml",
	    "price": "89.00",
	    "address": "济南市市中区建国小经三路47号汇苑家园对面",
	    "img": "14519577652122.jpg",
	    "unread": "0",
	    "create_time": "2016-11-17 15:11:02",
	    "fav_id": "13304",
	    "user_id": "2",
	    "circle": "玉函路",
	    "type": "goods",
	    "shop_name": "特百惠市委店"
	}*/
	
	String id,title,price,address,img,circle,shop_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
}
