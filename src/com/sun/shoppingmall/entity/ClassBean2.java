package com.sun.shoppingmall.entity;

import java.util.List;

public class ClassBean2 {
	//id
	private String id ;
	//标题
	private String title ;
	//分类
	private String sort ;

	private List<ClassBean3> items ;
	
	private List<ProduceNearbyBean> itemss ;
	
	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	private String orderTime ;
	
	public List<ClassBean3> getItems() {
		return items;
	}

	public void setItems(List<ClassBean3> items) {
		this.items = items;
	}
	
	public void setItemss (List<ProduceNearbyBean> itmess){
		this.itemss = itmess  ;
	}
	
	public List<ProduceNearbyBean> getItemss(){
		
		return itemss ;
	}

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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
