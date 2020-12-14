package org.sampratistaana.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.sampratistaana.Messages;

@Entity
@Table(name = "BANK_ACCOUNTS")
public class BankAccount {
	@Column(name = "ACCOUNT_NO", nullable = false)
	private String accountNo;
	
	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getFormattedBankName() {
		return Messages.getMessage(getBankName());
	}
}
