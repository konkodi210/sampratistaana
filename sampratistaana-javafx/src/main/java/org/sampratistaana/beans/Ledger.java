package org.sampratistaana.beans;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "LEDGER")
public class Ledger implements Serializable{
	private static final long serialVersionUID = -1797795357655017900L;

	public enum EntryType{CREDIT,DEBIT}
	public enum EntryCategory{MEMBER, DONATION, BOOK_SALE,BOOK_PURCHASE,EXPENSE}
	public enum TransactionMode {CASH, CHEQUE, ONLINE, DD}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENTRY_NO", nullable = false, insertable = false, updatable = false)
	private long entryNo;
	
	@Column(name = "ENTRY_TYPE", nullable = false)
	private EntryType entryType;
	
	@Column(name="ENTRY_CATEGORY", nullable = false)
	private EntryCategory entryCategory;
	
	@Column(name = "ENTRY_VALUE", nullable = false)
	private double entryValue;
	
	@Column(name = "ENTRY_DATE", nullable = false)
	private LocalDate entryDate;
	
	@Column(name = "MODE_OF_TRAN", nullable = false)
	private TransactionMode modeOfTranscation;
	
	@Column(name = "EXTERNAL_TRAN_ID")
	private String externalTranNo;
	
	@Column(name = "PAN_NO")
	private String panNo;
	
	@Column(name="ENTRY_DESC")
	private String entryDesc;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BANK_ACCOUNT_ID")
	private BankAccount bankAccount;
	
	public long getEntryNo() {
		return entryNo;
	}
	
	public Ledger setEntryNo(long entryNo) {
		this.entryNo = entryNo;
		return this;
	}
	public EntryType getEntryType() {
		return entryType;
	}
	public Ledger setEntryType(EntryType entryType) {
		this.entryType = entryType;
		return this;
	}
	public EntryCategory getEntryCategory() {
		return entryCategory;
	}
	public Ledger setEntryCategory(EntryCategory entryCategory) {
		this.entryCategory = entryCategory;
		return this;
	}
	public double getEntryValue() {
		return entryValue;
	}
	public Ledger setEntryValue(double entryValue) {
		this.entryValue = entryValue;
		return this;
	}
	public LocalDate getEntryDate() {
		return entryDate;
	}
	public Ledger setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
		return this;
	}
	public TransactionMode getModeOfTranscation() {
		return modeOfTranscation;
	}
	public Ledger setModeOfTranscation(TransactionMode modeOfTranscation) {
		this.modeOfTranscation = modeOfTranscation;
		return this;
	}	
	public String getExternalTranNo() {
		return externalTranNo;
	}
	public Ledger setExternalTranNo(String externalTranNo) {
		this.externalTranNo = externalTranNo;
		return this;
	}
	public String getPanNo() {
		return panNo;
	}
	public Ledger setPanNo(String panNo) {
		this.panNo = panNo;
		return this;
	}	
	public String getEntryDesc() {
		return entryDesc;
	}

	public Ledger setEntryDesc(String entryDesc) {
		this.entryDesc = entryDesc;
		return this;
	}
	
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public Ledger setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
		return this;
	}

	@Override
	public String toString() {
		return "Ledger [entryNo=" + entryNo + ", entryType=" + entryType + ", entryCategory=" + entryCategory
				+ ", entryValue=" + entryValue + ", entryDate=" + entryDate + ", modeOfTranscation=" + modeOfTranscation
				+ ", externalTranNo=" + externalTranNo + ", panNo=" + panNo + ", entryDesc=" + entryDesc
				+ ", bankAccount=" + bankAccount + "]";
	}
}
