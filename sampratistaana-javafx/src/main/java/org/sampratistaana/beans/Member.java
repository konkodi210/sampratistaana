package org.sampratistaana.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
//@PrimaryKeyJoinColumn(name = "LEDGER_ENTRY_NO")
public class Member implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_NO", nullable = false)
	private long memberNo;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")
	private Ledger ledger;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "NICK_NAME")
	private String nickName;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="MEMBERSHIP_TYPE")
	private String membershipType;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DATE_OF_BIRTH")
	private long dateOfBirth;
	
	public Ledger getLedger() {
		return ledger;
	}

	public Member setLedger(Ledger ledger) {
		this.ledger = ledger;
		return this;
	}

	public long getMemberNo() {
		return memberNo;
	}

	public Member setMemberNo(long memberNo) {
		this.memberNo = memberNo;
		return this;
	}

	public String getName() {
		return name;
	}

	public Member setName(String name) {
		this.name = name;
		return this;
	}

	public String getNickName() {
		return nickName;
	}

	public Member setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Member setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public Member setMembershipType(String membershipType) {
		this.membershipType = membershipType;
		return this;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public Member setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
		return this;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public Member setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Member setEmail(String email) {
		this.email = email;
		return this;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public Member setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", ledger=" + ledger + ", name=" + name + ", nickName=" + nickName
				+ ", address=" + address + ", membershipType=" + membershipType + ", mobileNo=" + mobileNo
				+ ", phoneNo=" + phoneNo + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
