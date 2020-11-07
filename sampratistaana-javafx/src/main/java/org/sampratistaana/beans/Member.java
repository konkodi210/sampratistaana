package org.sampratistaana.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member extends Ledger {

	@Id @GeneratedValue
	@Column(name = "MEMBER_NO")
	private long memberNo;

	@Column(name = "LEDGER_ENTRY_NO", nullable = false)
	private long ledgerEntryNo;

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
	private int dateOfBirth;

	public long getMemberNo() {
		return memberNo;
	}

	public Member setMemberNo(long memberNo) {
		this.memberNo = memberNo;
		return this;
	}

	public long getLedgerEntryNo() {
		return ledgerEntryNo;
	}

	public Member setLedgerEntryNo(long ledgerEntryNo) {
		this.ledgerEntryNo = ledgerEntryNo;
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

	public int getDateOfBirth() {
		return dateOfBirth;
	}

	public Member setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}
}
