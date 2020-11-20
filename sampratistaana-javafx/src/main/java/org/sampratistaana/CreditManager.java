package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.dbSession;

import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sampratistaana.beans.BookSale;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Inventory.InventoryType;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;

public class CreditManager {

	/**
	 * Saves the member into the database
	 * @param member 
	 * @return Member number 
	 */
	public Long saveMember(Member member) {
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(member);
			tran.commit();
			return member.getMemberNo();
		}
	}

	/**
	 * Fetches the Member by id
	 * @param memberId
	 * @return
	 */
	public Member getMember(long memberId) {
		try(Session session=dbSession()){
			return session.get(Member.class, memberId);
		}
	}

	/**
	 * get all members from the database.
	 * @return List of all members sorted by Creation date in descending order.
	 */
	public List<Member> getAllMembers(){
		// Even though we have mentioned in lazy loading for Ledger in member bean, we are fetching to avoid multiple child queries
		try(Session session=dbSession()){			
			return session
					.createQuery("SELECT m FROM Member as m INNER JOIN FETCH m.ledger ORDER BY m.ledger.entryDate DESC", Member.class)
					.getResultList();
		}
	}

	/**
	 * Saves the Donation into the database
	 * @param donation 
	 * @return Member number 
	 */
	public Long saveDonation(Donation donation) {
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(donation);
			tran.commit();
			return donation.getDonationId();			
		}
	}

	public void deleteMember(Member member) {
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.delete(member);
			tran.commit();
		}
	}

	/**
	 * Fetches the Donation by id
	 * @param donationId
	 * @return
	 */
	public Donation getDonation(long donationId) {
		try(Session session=dbSession()){
			return session.get(Donation.class, donationId);
		}
	}

	/**
	 * get all Donation from the database.
	 * @return List of all members sorted by Creation date in descending order.
	 */
	public List<Donation> getAllDonations(){
		// Even though we have mentioned in lazy loading for Ledger in member bean, we are fetching to avoid multiple child queries
		try(Session session=dbSession()){			
			return session
					.createQuery("SELECT m FROM Donation as m INNER JOIN FETCH m.ledger ORDER BY m.ledger.entryDate DESC", Donation.class)
					.getResultList();
		}
	}

	public void deleteDonation(Donation donation) {
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.delete(donation);
			tran.commit();
		}
	}


	/**
	 * Saves the Inventory into the database
	 * @param inventory 
	 * @return Member number 
	 */
	public Long saveInventory(Inventory inventory) {
		try(Session session=dbSession()){			
			session.saveOrUpdate(inventory);
			return inventory.getInventoryId();
		}
	}

	/**
	 * Fetches the Inventory by id
	 * @param inventoryId
	 * @return
	 */
	public Inventory getInventory(long inventoryId) {
		try(Session session=dbSession()){
			return session.get(Inventory.class, inventoryId);
		}
	}
	
	public List<Inventory> getInventory(InventoryType type){
		try(Session session=dbSession()){
			return session
					.createQuery("SELECT i FROM Inventory AS i WHERE i.inventoryType=:type",Inventory.class)
					.setParameter("type", type)
					.getResultList();
		}
	}

	/**
	 * This is temporary function until inventory management screen comes
	 */
	public void loadBookInventory() {
		ResourceBundle res=Messages.getResource();
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			for(Enumeration<String> enm=res.getKeys();enm.hasMoreElements();) {
				String key=enm.nextElement();
				if(key.startsWith("book.")) {
					Inventory inv=session.byNaturalId(Inventory.class).using("unitName", res.getString(key)).load();
					if(inv==null) {
						session.saveOrUpdate(new Inventory()
								.setInventoryType(InventoryType.BOOK)
								.setUnitName(res.getString(key))
								.setUnitPrice(250)
								.setLedger(new Ledger()
										.setEntryType(EntryType.DEBIT)
										.setEntryCategory(EntryCategory.BOOK_PURCHASE)
										.setEntryValue(250*101)
										.setEntryDate(System.currentTimeMillis())
										.setModeOfTranscation(TransactionMode.CASH))
								);
					}
				}
			}
			tran.commit();
		}
	}

	public void makeBookSale(BookSale bookSale) {
		Transaction tran=null;
		try(Session session=dbSession()){
			tran=session.beginTransaction();			

			//Update the inventory
			Inventory inv=bookSale.getInventory();
			int currentInventory = inv.getInventoryCount();
			if(currentInventory < bookSale.getUnitCount()) {
				throw new SampratistaanaException("book.nostock",currentInventory,bookSale.getUnitCount());
			}
			inv.setInventoryCount(currentInventory - bookSale.getUnitCount());

			//Update the Ledger entry 
			bookSale.getLedger()
			.setEntryCategory(EntryCategory.BOOK_SALE)
			.setEntryType(EntryType.CREDIT)
			.setEntryValue(bookSale.getUnitCount() * inv.getUnitPrice())
			.setEntryDate(System.currentTimeMillis());

			session.saveOrUpdate(bookSale);
			tran.commit();
		}catch(Exception e) {
			//			if(tran!=null) {
			//				tran.rollback();
			//			}
			throw new SampratistaanaException(e);
		}
	}
}
