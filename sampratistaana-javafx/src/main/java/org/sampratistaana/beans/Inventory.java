package org.sampratistaana.beans;

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

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "INVENTORY")
public class Inventory implements Serializable{
	private static final long serialVersionUID = 7056284966345612238L;

	public enum InventoryType{ BOOK }

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVENTORY_ID", nullable = false)	
	private long inventoryId;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "LEDGER_ENTRY_NO")
	private Ledger ledger;
	
	@Column(name = "INVENTORY_TYPE", nullable = false)
	private InventoryType inventoryType;
	
	@NaturalId
	@Column(name = "UNIT_NAME", nullable = false, unique =  true)
	private String unitName;
	
	@Column(name = "UNIT_PRICE", nullable = false)
	private double unitPrice;
	
	@Column(name = "INVENTROY_COUNT", nullable = false)
	private int inventoryCount;

	public long getInventoryId() {
		return inventoryId;
	}

	public Inventory setInventoryId(long inventoryId) {
		this.inventoryId = inventoryId;
		return this;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public Inventory setLedger(Ledger ledger) {
		this.ledger = ledger;
		return this;
	}

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public Inventory setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
		return this;
	}

	public String getUnitName() {
		return unitName;
	}

	public Inventory setUnitName(String unitName) {
		this.unitName = unitName;
		return this;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public Inventory setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public int getInventoryCount() {
		return inventoryCount;
	}

	public Inventory setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
		return this;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", ledger=" + ledger + ", inventoryType=" + inventoryType
				+ ", unitName=" + unitName + ", unitPrice=" + unitPrice + ", inventoryCount=" + inventoryCount + "]";
	}

}
