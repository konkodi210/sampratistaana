package org.sampratistaana.beans;

import static org.sampratistaana.Messages.formatDate;

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

import org.sampratistaana.beans.Ledger.TransactionMode;

@Entity
@Table(name = "DONATION")
public class Donation implements Serializable{
	private static final long serialVersionUID = -5141179747438664712L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DONATION_ID", nullable = false)
	private long donationId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")
	private Ledger ledger;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "NICK_NAME")
	private String nickName;

	@Column(name="ADDRESS")
	private String address;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "DATE_OF_BIRTH")
	private long dateOfBirth;

	public long getDonationId() {
		return donationId;
	}

	public Donation setDonationId(long donationId) {
		this.donationId = donationId;
		return this;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public Donation setLedger(Ledger ledger) {
		this.ledger = ledger;
		return this;
	}

	public String getName() {
		return name;
	}

	public Donation setName(String name) {
		this.name = name;
		return this;
	}

	public String getNickName() {
		return nickName;
	}

	public Donation setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Donation setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public Donation setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
		return this;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public Donation setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Donation setEmail(String email) {
		this.email = email;
		return this;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public Donation setDateOfBirth(long dateOfBirth) {
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
	
	public double getDonationValue() {
		return getLedger().getEntryValue();
	}
	
	public String getDate() {
		if(getLedger()!=null) {
			return formatDate(getLedger().getEntryDate());
		}
		return null;
	}

	@Override
	public String toString() {
		return "Donation [donationId=" + donationId + ", ledger=" + ledger + ", name=" + name + ", nickName=" + nickName
				+ ", address=" + address + ", mobileNo=" + mobileNo + ", phoneNo=" + phoneNo + ", email=" + email
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	
	
}
