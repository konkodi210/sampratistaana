package org.sampratistaana.beans;

import java.time.LocalDate;

import org.sampratistaana.Messages;
import org.sampratistaana.beans.Ledger.TransactionMode;

public class BookSaleUIList {
	private long ledgerEntryNo;
	private String bookNames;
	private String customerName;
	private double entryValue;
	private LocalDate entryDate;
	private TransactionMode modeOfTran;
	private String externalTranId;
	
	public long getLedgerEntryNo() {
		return ledgerEntryNo;
	}
	public BookSaleUIList setLedgerEntryNo(long ledgerEntryNo) {
		this.ledgerEntryNo = ledgerEntryNo;
		return this;
	}
	public String getBookNames() {
		return bookNames;
	}
	public BookSaleUIList setBookNames(String bookNames) {
		this.bookNames = bookNames;
		return this;
	}
	public String getCustomerName() {
		return customerName;
	}
	public BookSaleUIList setCustomerName(String customerName) {
		this.customerName = customerName;
		return this;
	}
	public double getEntryValue() {
		return entryValue;
	}
	public BookSaleUIList setEntryValue(double entryValue) {
		this.entryValue = entryValue;
		return this;
	}
	public String getEntryDate() {
		return Messages.formatDate(entryDate);
	}
	public BookSaleUIList setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
		return this;
	}
	public TransactionMode getModeOfTran() {
		return modeOfTran;
	}
	public BookSaleUIList setModeOfTran(TransactionMode modeOfTran) {
		this.modeOfTran = modeOfTran;
		return this;
	}
	public String getExternalTranId() {
		return externalTranId;
	}
	public BookSaleUIList setExternalTranId(String externalTranId) {
		this.externalTranId = externalTranId;
		return this;
	}
}
