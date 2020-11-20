package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.sampratistaana.TestUtils.createDonation;
import static org.sampratistaana.TestUtils.createInventory;
import static org.sampratistaana.TestUtils.createMember;

import java.util.List;

import org.junit.Test;
import org.sampratistaana.beans.BookSale;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;

public class CreditOperationTest {

	@Test
	public void testSaveMember() throws Exception {
		Member member=createMember();				
		new CreditManager().saveMember(member);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)member.getLedger().getEntryNo(),greaterThan(0));
		assertThat("Member id should auto generated", (int)member.getMemberNo(),greaterThan(0));

	}

	@Test
	public void testGetMember() throws Exception {
		Member member=createMember();				
		new CreditManager().saveMember(member);
		Member memberFromDb = new CreditManager().getMember(member.getMemberNo());
		assertThat("Member record must be found", memberFromDb,notNullValue());
		assertThat("A ledger record must be assosiated with that", memberFromDb.getLedger(),notNullValue());
		assertThat("Both Objects should match", member.toString(),equalTo(memberFromDb.toString()));
	}

	@Test
	public void testGetAllMember() {		
		new CreditManager().saveMember(createMember());
		List<Member> memberList=new CreditManager().getAllMembers();
		assertThat("List should not be null", memberList, notNullValue());
		assertThat("Must have more than one member", memberList.size(),greaterThan(0));
	}

	@Test
	public void testSaveDonation() throws Exception {
		Donation donation=createDonation();				
		new CreditManager().saveDonation(donation);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)donation.getLedger().getEntryNo(),greaterThan(0));
		assertThat("Donation id should auto generated", (int)donation.getDonationId(),greaterThan(0));
	}

	@Test
	public void testGetDonation() throws Exception {
		Donation donation=createDonation();				
		new CreditManager().saveDonation(donation);
		Donation donationFromDb = new CreditManager().getDonation(donation.getDonationId());
		assertThat("Donation record must be found", donationFromDb,notNullValue());
		assertThat("A ledger record must be assosiated with that", donationFromDb.getLedger(),notNullValue());
		assertThat("Both Objects should match", donation.toString(),equalTo(donationFromDb.toString()));
	}

	@Test
	public void testGetAllDonation() {				
		new CreditManager().saveDonation(createDonation());
		List<Donation> donationList=new CreditManager().getAllDonations();
		assertThat("List should not be null", donationList, notNullValue());
		assertThat("Must have more than one member", donationList.size(),greaterThan(0));
	}

	@Test
	public void testSaveInventory() throws Exception {
		Inventory inventory=createInventory();				
		new CreditManager().saveInventory(inventory);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)inventory.getLedger().getEntryNo(),greaterThan(0));
		assertThat("Inventory id should auto generated", (int)inventory.getInventoryId(),greaterThan(0));

	}

	@Test
	public void testGetInventory() throws Exception {
		Inventory inventory=createInventory();				
		new CreditManager().saveInventory(inventory);
		Inventory inventoryFromDb = new CreditManager().getInventory(inventory.getInventoryId());
		assertThat("Inventory record must be found", inventoryFromDb,notNullValue());
		assertThat("A ledger record must be assosiated with that", inventoryFromDb.getLedger(),notNullValue());
		assertThat("Both Objects should match", inventory.toString(),equalTo(inventoryFromDb.toString()));
	}

	@Test
	public void testMakeSale() throws Exception {
		Inventory inventory=createInventory();
		int currentInventory=inventory.getInventoryCount();
		BookSale bookSale=new BookSale()
				.setInventory(inventory)
				.setCustomerName("Good Hearted Man")
				.setUnitCount(5)
				.setLedger(new Ledger()
						.setModeOfTranscation(TransactionMode.CASH)
						);
		new CreditManager().makeBookSale(bookSale);
		assertThat("Booksale id must be generated", (int)bookSale.getBookSaleId(),greaterThan(0));
		assertThat("Inventory count should reduced", 
				inventory.getInventoryCount(),equalTo(currentInventory-bookSale.getUnitCount()));
		assertThat("Total Prize should multiplication of unit count and unit price",
				bookSale.getLedger().getEntryValue(), equalTo(bookSale.getUnitCount()*inventory.getUnitPrice()));
	}
}
