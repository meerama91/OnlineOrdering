package com.cmpe275.OnlineOrdering;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(MenuPopularityId.class)
@Table(name = "menupopularity")
public class MenuPopularity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7939451737351308214L;
	@Id
	private int orderid;
	@Id
	private String name;
	private String category;
	private int quantity;
	private String orderdate;
	private String ordertime;
	
	public MenuPopularity() {
		
	}
	
	public MenuPopularity(int orderid,
			String name,
			String category,
			long quantity,
			String orderdate,
			String ordertime) {
		this.orderid = orderid;
		this.name = name;
		this.category = category;
		this.quantity = (int) quantity;
		this.orderdate = orderdate;
		this.ordertime = ordertime;
	}
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
}

class MenuPopularityId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2268622224943648573L;
	int orderid;
	String name;
}
