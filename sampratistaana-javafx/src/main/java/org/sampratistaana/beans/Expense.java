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

import org.sampratistaana.Messages;

@Entity
@Table(name = "EXPENSE")
public class Expense implements Serializable{
	
	private static final long serialVersionUID = -5530011179623449836L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXPENSE_ID", nullable = false)
	private long expenseId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")	
	private Ledger ledger;

	@Column(name = "EXPENSE_TYPE", nullable = false)
	private String expenseType;
	
	@Column(name = "FUND_TYPE", nullable = false)
	private String fundType;
	
	public Ledger getLedger() {
		return ledger;
	}
	
	public Expense setLedger(Ledger ledger) {
		this.ledger = ledger;
		setEntryDesc();
		return this;
	}

	public long getExpenseId() {
		return expenseId;
	}

	public Expense setExpenseId(long expenseId) {
		this.expenseId = expenseId;
		return this;
	}

	public String getType() {
		return Messages.getMessage(getExpenseType());
	}

	public String getFund() {
		return Messages.getMessage(getFundType());
	}
	
	public Expense setExpenseType(String expenseType) {
		this.expenseType = expenseType;
		setEntryDesc();
		return this;
	}
	

	public String getExpenseType() {
		return expenseType;
	}
	
	public String getFundType() {
		return fundType;
	}

	public Expense setFundType(String fundType) {
		this.fundType = fundType;
		return this;
	}

	public String getAmount() {
		return Messages.formatCurrency(getLedger().getEntryValue());
	}
	
	public String getDate() {
		if(getLedger()!=null) {
			return formatDate(getLedger().getEntryDate());
		}
		return null;
	}
	public String getPaymentType() {
		return getLedger().getModeOfTranscation().name();
	}
	
	public String getTransaction() {
			return getLedger().getExternalTranNo();
	}
	public String getDescription() {
		return getLedger().getEntryDesc();
	}
	
	private void setEntryDesc() {
		if(getLedger()!=null && getExpenseType()!=null) {
			getLedger().setEntryDesc(getExpenseType());
		}
	}
	
	public long getReceiptNum() {
		//return ledger!=null?ledger.getEntryNo():0;
		return getExpenseId();
	}

	@Override
	public String toString() {
		return "Expense [expenseId=" + expenseId + ", ledger=" + ledger + ", expenseType=" + expenseType + "]";
	}

	
}
