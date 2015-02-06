package com.paypal.first.contactsadapter;

public class ContactBean {
	private String name;
	private String phoneNo;
	private String checkbox;
	private boolean isChecked = false;

	
	public boolean isChecked() {
		return isChecked;
	}
	

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
