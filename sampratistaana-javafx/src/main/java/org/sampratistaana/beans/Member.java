package org.sampratistaana.beans;

import static org.sampratistaana.Messages.formatDate;

import java.io.Serializable;
import java.time.LocalDate;

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

import org.sampratistaana.Messages;
import org.sampratistaana.beans.Ledger.TransactionMode;

@Entity
@Table(name = "MEMBER")
public class Member implements Serializable{
	private static final long serialVersionUID = 2435744732575061197L;
	public enum MembershipType {LIFE, YEARLY}
	public enum MemberStatus{ACTIVE,EXPIRED,DEAD}

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

	@Column(name="MEMBERSHIP_TYPE", nullable = false)
	private MembershipType membershipType=MembershipType.LIFE;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;
	
	@Column(name="MEMBER_STATUS", nullable = false)
	private MemberStatus memberStatus=MemberStatus.ACTIVE;
	
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

	public MembershipType getMembershipType() {
		return membershipType;
	}

	public Member setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
		reCaliculateMemberStatus();
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Member setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}
	
	public String getPaymentType() {
		if(getLedger()!=null) {
			TransactionMode mode=getLedger().getModeOfTranscation();
			return mode!=null?mode.toString():null;
		}
		return null;
	}
	
	public double getMembershipFee() {
		return getLedger().getEntryValue();
	}
	
	public String getDate() {
		if(getLedger()!=null) {
			return formatDate(getLedger().getEntryDate());
		}
		return null;
	}
	
	public MemberStatus getMemberStatus() {
		reCaliculateMemberStatus();
		return memberStatus;
	}

	public Member setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
		return this;
	}
	
	public void reCaliculateMemberStatus() {
		if(memberStatus==MemberStatus.EXPIRED) {
			//do Nothing. 
		}else if(membershipType==MembershipType.YEARLY &&
				ledger!=null && ledger.getEntryDate().getYear()<LocalDate.now().getYear()) {
			memberStatus=MemberStatus.EXPIRED;
		}
	}
	
	public String getMemberStatusLocalized() {
		return Messages.getMessage(getMemberStatus().toString());
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", ledger=" + ledger + ", name=" + name + ", nickName=" + nickName
				+ ", address=" + address + ", membershipType=" + membershipType + ", mobileNo=" + mobileNo
				+ ", phoneNo=" + phoneNo + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
