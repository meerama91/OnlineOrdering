package com.cmpe275.OnlineOrdering;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the main menu item class containing the various menu items. All the
 * fields are private in nature and getters and setters are written for them *
 */

@Entity
@Table(name = "tempuser")
public class TempUser {

	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String email;
	private String code;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TempUser() {

	}

}