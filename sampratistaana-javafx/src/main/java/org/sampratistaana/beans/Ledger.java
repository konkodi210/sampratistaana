package org.sampratistaana.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "LEDGER")
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("unchecked")
public class Ledger<T extends Ledger<T>> {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENTRY_NO", nullable = false, insertable = false, updatable = false)
	private long entryNo;
	
	@Column(name = "ENTRY_TYPE", nullable = false)
	private String entryType;
	
	@Column(name="ENTRY_CATEGORY", nullable = false)
	private String entryCategory;
	
	@Column(name = "ENTRY_VALUE", nullable = false)
	private double entryValue;
	
	@Column(name = "ENTRY_DATE", nullable = false)
	private long entryDate;
	
	@Column(name = "MODE_OF_TRAN", nullable = false)
	private String modeOfTranscation;
	
	@Column(name = "EXTERNAL_TRAN_ID")
	private String externalTranNo;
	
	@Column(name = "PAN_NO")
	private String panNo;
	
	public long getEntryNo() {
		return entryNo;
	}
	
	public T setEntryNo(long entryNo) {
		this.entryNo = entryNo;
		return (T)this;
	}
	public String getEntryType() {
		return entryType;
	}
	public T setEntryType(String entryType) {
		this.entryType = entryType;
		return (T)this;
	}
	public String getEntryCategory() {
		return entryCategory;
	}
	public T setEntryCategory(String entryCategory) {
		this.entryCategory = entryCategory;
		return (T)this;
	}
	public double getEntryValue() {
		return entryValue;
	}
	public T setEntryValue(double entryValue) {
		this.entryValue = entryValue;
		return (T)this;
	}
	public long getEntryDate() {
		return entryDate;
	}
	public T setEntryDate(long entryDate) {
		this.entryDate = entryDate;
		return (T)this;
	}
	public String getModeOfTranscation() {
		return modeOfTranscation;
	}
	public T setModeOfTranscation(String modeOfTranscation) {
		this.modeOfTranscation = modeOfTranscation;
		return (T)this;
	}	
	public String getExternalTranNo() {
		return externalTranNo;
	}
	public T setExternalTranNo(String externalTranNo) {
		this.externalTranNo = externalTranNo;
		return (T)this;
	}
	public String getPanNo() {
		return panNo;
	}
	public T setPanNo(String panNo) {
		this.panNo = panNo;
		return (T)this;
	}
	
}
