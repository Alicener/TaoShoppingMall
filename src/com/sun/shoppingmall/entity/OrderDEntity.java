package com.sun.shoppingmall.entity;

public class OrderDEntity {

	/*{
	    "id": "1534",
	    "order_id": "1456",
	    "goods_id": "674",
	    "product_id": "301",
	    "product_attr": "4G，白色，预存200元",
	    "number": "2",
	    "price": "2698.00",
	    "real_price": "0.00",
	    "grade": "0",
	    "evaluate": "",
	    "shop_grade": "0",
	    "evaluate_time": "",
	    "xb": "0",
	    "total_xb": "0",
	    "is_refund": "0",
	    "attr1_id": "939",
	    "attr2_id": "949",
	    "attr3_id": "22657",
	    "title": "华为P8",
	    "img": "14405091493439.png",
	    "attr1_name": "4G",
	    "attr2_name": "白色",
	    "attr3_name": "预存200元"
	}*/
	
	private String id,order_id,goods_id,product_id,product_attr,number,price,real_price,title,img;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_attr() {
		return product_attr;
	}

	public void setProduct_attr(String product_attr) {
		this.product_attr = product_attr;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReal_price() {
		return real_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
