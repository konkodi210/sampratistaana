package org.sampratistaana.beans;

import static org.sampratistaana.Messages.formatDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MemberListUI {
	private StringProperty action;
	private StringProperty name;
	private StringProperty nickname;
	private StringProperty type;
	private StringProperty date;
	private StringProperty phone;
	private StringProperty email;
	private StringProperty paymentType;
	private StringProperty mobile;
	private StringProperty address;
	private StringProperty description;
	private StringProperty dob;

	public static MemberListUI of(Member member) {
		MemberListUI ui=new MemberListUI();
		ui.setName(member.getName());
		ui.setNickname(ui.getName());
		ui.setType(member.getMembershipType());
		ui.setDate(formatDate(member.getLedger().getEntryDate()));
		ui.setPhone(member.getPhoneNo());
		ui.setMobile(member.getMobileNo());
		ui.setAddress(member.getAddress());
		ui.setEmail(member.getEmail());
		ui.setDob(formatDate(member.getDateOfBirth()));
		return ui;
	}
	
	public void setAction(String value) {
		actionProperty().set(value); 
	}
	public String getAction() {
		return actionProperty().get(); 
	}
	public StringProperty actionProperty() {
		if (action == null) action = new SimpleStringProperty(this, "action");
		return action; 
	}


	public void setName(String value) { 
		nameProperty().set(value); 
	}
	public String getName() { 
		return nameProperty().get(); 
	}
	public StringProperty nameProperty() {
		if (name == null) name = new SimpleStringProperty(this, "name");
		return name; 
	}


	public void setNickname(String value) { 
		nicknameProperty().set(value); 
	}
	public String getNickname() { 
		return nicknameProperty().get(); 
	}
	public StringProperty nicknameProperty() {
		if (nickname == null) nickname = new SimpleStringProperty(this, "nickname");
		return nickname; 
	}


	public void setType(String value) { 
		typeProperty().set(value); 
	}
	public String getType() { 
		return typeProperty().get();
	}
	public StringProperty typeProperty() {
		if (type == null) type = new SimpleStringProperty(this, "type");
		return type; 
	}


	public void setDate(String value) { 
		dateProperty().set(value); 
	}
	public String getDate() { 
		return dateProperty().get(); 
	}
	public StringProperty dateProperty() {
		if (date == null) date = new SimpleStringProperty(this, "date");
		return date; }


	public void setEmail(String value) { 
		emailProperty().set(value); 
	}
	public String getEmail() { 
		return emailProperty().get();
	}
	public StringProperty emailProperty() {
		if (email == null) email = new SimpleStringProperty(this, "email");
		return email; 
	}


	public void setMobile(String value) {
		mobileProperty().set(value); 
	}
	public String getMobile() { 
		return mobileProperty().get(); 
	}
	public StringProperty mobileProperty() {
		if (mobile == null) mobile = new SimpleStringProperty(this, "mobile");
		return mobile;
	}


	public void setPhone(String value) { 
		phoneProperty().set(value); 
	}
	public String getPhone() { 
		return phoneProperty().get(); 
	}
	public StringProperty phoneProperty() {
		if (phone == null) phone = new SimpleStringProperty(this, "phone");
		return phone; 
	}


	public void setPaymentType(String value) { 
		paymentTypeProperty().set(value);
	}
	public String getPaymentType() { 
		return paymentTypeProperty().get(); 
	}
	public StringProperty paymentTypeProperty() {
		if (paymentType == null) paymentType = new SimpleStringProperty(this, "paymentType");
		return paymentType; 
	}


	public void setAddress(String value) { 
		addressProperty().set(value); 
	}
	public String getAddress() {
		return addressProperty().get();
	}
	public StringProperty addressProperty() {
		if (address == null) address = new SimpleStringProperty(this, "address");
		return address; 
	}


	public void setDob(String value) { 
		dobProperty().set(value); 
	}
	public String getDob() { 
		return dobProperty().get(); 
	}
	public StringProperty dobProperty() {
		if (dob == null) dob = new SimpleStringProperty(this, "dob");
		return dob; 
	}


	public void setDescription(String value) {
		descriptionProperty().set(value);
	}
	public String getDescription() { 
		return descriptionProperty().get(); 
	}
	public StringProperty descriptionProperty() {
		if (description == null) description = new SimpleStringProperty(this, "description");
		return description; 
	}

}
