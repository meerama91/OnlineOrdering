package com.cmpe275.OnlineOrdering;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Mapped to order details table of the db
 * @author Somya
 *
 */
@Entity @IdClass(OrderDetailsId.class)
@Table(name = "orderdetails")
public class OrderDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -847676040253140856L;

	@Id
	private int userid;
	
	@Id
	private int orderid;

	private String menu_items;

	private String status;
	
	@Column(name="pickup_date")
	private String pickup_date;
	
	@Column(name="pickup_time")
	private String pickup_time;
	
	private float price;
	
	private String orderdate;
	
	private String ordertime;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(String menu_items) {
		this.menu_items = menu_items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.userid + ": " + this.orderid;
	}

	public String getpickup_date() {
		return pickup_date;
	}

	public void setpickup_date(String pickupDate) {
		this.pickup_date = pickupDate;
	}

	public String getpickup_time() {
		return pickup_time;
	}

	public void setpickup_time(String pickupTime) {
		this.pickup_time = pickupTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

class OrderDetailsId implements Serializable {
	private static final long serialVersionUID = 7223242979687916889L;
	int orderid;
    int userid;
}
