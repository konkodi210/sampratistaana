package org.sampratistaana.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LEDGER")
public class Ledger {
	public enum EntryType{CREDIT,DEBIT}
	public enum EntryCategory{MEMBER, DONATION, BOOK_SALE,BOOK_PURCHASE}
	public enum TransactionMode {CASH, CHECK, ONLINE}
	
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
	private long entryDate;
	
	@Column(name = "MODE_OF_TRAN", nullable = false)
	private TransactionMode modeOfTranscation;
	
	@Column(name = "EXTERNAL_TRAN_ID")
	private String externalTranNo;
	
	@Column(name = "PAN_NO")
	private String panNo;
	
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
	public long getEntryDate() {
		return entryDate;
	}
	public Ledger setEntryDate(long entryDate) {
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

	@Override
	public String toString() {
		return "Ledger [entryNo=" + entryNo + ", entryType=" + entryType + ", entryCategory=" + entryCategory
				+ ", entryValue=" + entryValue + ", entryDate=" + entryDate + ", modeOfTranscation=" + modeOfTranscation
				+ ", externalTranNo=" + externalTranNo + ", panNo=" + panNo + "]";
	}
	
}
