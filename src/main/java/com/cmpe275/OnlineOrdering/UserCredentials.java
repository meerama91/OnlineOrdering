package com.cmpe275.OnlineOrdering;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	/** This is the main menu item class containing the various menu items.
	 * All the fields are private in nature and getters and setters are written for them
	 * *
	 */

	@Entity
	@Table(name = "usercredentials")
	public class UserCredentials {
		
		@Id @Column(name="user_id")
		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		private String email;
		private String password;
		private String fullname;
		private String address;
		private String phone;
		
		
		
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getFullname() {
			return fullname;
		}

		public void setFullname(String fullname) {
			this.fullname = fullname;
		}
		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	}

