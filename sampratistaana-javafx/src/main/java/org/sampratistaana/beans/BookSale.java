package org.sampratistaana.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BOOK_SALE")
public class BookSale implements Serializable{
	private static final long serialVersionUID = 3360702599003533050L;	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_SALE_ID", nullable = false)
	private long bookSaleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")
	private Ledger ledger;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_ID")
	private Inventory inventory;
	
	@Column(name = "UNIT_COUNT", nullable = false)
	private int unitCount;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "SELLER_NAME")
	private String sellerName;
	
	@Transient
	private int oldUnitCount;
	
	public long getBookSaleId() {
		return bookSaleId;
	}

	public BookSale setBookSaleId(long bookSaleId) {
		this.bookSaleId = bookSaleId;
		return this;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public BookSale setLedger(Ledger ledger) {
		this.ledger = ledger;
		return this;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public BookSale setInventory(Inventory inventory) {
		this.inventory = inventory;
		return this;
	}

	public String getCustomerName() {
		return customerName;
	}

	public BookSale setCustomerName(String customerName) {
		this.customerName = customerName;
		return this;
	}
	

	public int getUnitCount() {
		return unitCount;
	}

	public BookSale setUnitCount(int unitCount) {
		this.oldUnitCount = this.unitCount;
		this.unitCount = unitCount;
		return this;
	}
	
	public int getOldUnitCount() {
		return this.oldUnitCount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public BookSale setSellerName(String sellerName) {
		this.sellerName = sellerName;
		return this;
	}

	@Override
	public String toString() {
		return "BookSale [bookSaleId=" + bookSaleId + ", ledger=" + ledger + ", inventory=" + inventory + ", unitCount="
				+ unitCount + ", customerName=" + customerName + ", sellerName=" + sellerName + "]";
	}
	
}
