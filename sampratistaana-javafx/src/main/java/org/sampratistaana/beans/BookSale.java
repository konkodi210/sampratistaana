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
}
