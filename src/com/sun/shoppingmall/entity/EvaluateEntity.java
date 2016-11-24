package com.sun.shoppingmall.entity;

public class EvaluateEntity {
	/*{
	    "id": "135",
	    "title": "发卡",
	    "product_attr": "",
	    "img": "14568855994983.jpg",
	    "price": "2.00",
	    "number": "1",
	    "creation_time": "2016-03-02 10:36:36"
	}*/
	
	String id,title,img,product_attr,price,number,creation_time;

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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getProduct_attr() {
		return product_attr;
	}

	public void setProduct_attr(String product_attr) {
		this.product_attr = product_attr;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(String creation_time) {
		this.creation_time = creation_time;
	}
}
