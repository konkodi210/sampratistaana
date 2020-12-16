package org.sampratistaana.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sampratistaana.Messages;

@Entity
@Table(name = "BANK_ACCOUNTS")
public class BankAccount {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BANK_ACCOUNT_ID")
	private long bankAccountId;
	
	@Column(name = "ACCOUNT_NO", nullable = false)
	private String accountNo;
	
	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;
	
	public long getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
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
	@Override
	public String toString() {
		return Messages.getMessage(bankName);
	}	
}
