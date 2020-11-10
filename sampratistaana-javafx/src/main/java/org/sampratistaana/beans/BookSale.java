package org.sampratistaana.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK_SALE")
public class BookSale {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_SALE_ID", nullable = false)
	private long bookSaleId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")
	private Ledger ledger;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_ID")
	private Inventory inventory;
	
	@Column(name = "UNIT_COUNT", nullable = false)
	private int unitCount;
	
	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customerName;

	public long getBookSaleId() {
		return bookSaleId;
	}

	public void setBookSaleId(long bookSaleId) {
		this.bookSaleId = bookSaleId;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	@Override
	public String toString() {
		return "BookSale [bookSaleId=" + bookSaleId + ", ledger=" + ledger + ", inventory=" + inventory + ", unitCount="
				+ unitCount + ", customerName=" + customerName + "]";
	}
	
}
