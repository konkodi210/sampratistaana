package org.sampratistaana;

import java.time.LocalDate;
import java.util.UUID;

import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Inventory.InventoryType;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;

public class TestUtils {

	public static Member createMember() {
		return new Member()
				.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows")
				.setAddress("blah house\n blah post")
				.setMembershipType(MembershipType.LIFE)
				.setMobileNo("12345454")
				.setPhoneNo("23434234234")
				.setEmail("abc@gmail.com")
				.setDateOfBirth(LocalDate.now())
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.MEMBER)
						.setEntryValue(101)
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
				);	
	}
	
	public static Donation createDonation() {
		return new Donation()
				.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows")
				.setAddress("blah house\r\n blah post")
				.setMobileNo("12345454")
				.setPhoneNo("23434234234")
				.setEmail("abc@gmail.com")
				.setDateOfBirth(LocalDate.now())
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.DONATION)
						.setEntryValue(101)
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
				);	
	}
	
	public static Inventory createInventory() {
		return new Inventory()
				.setInventoryType(InventoryType.BOOK)
				.setUnitName(UUID.randomUUID().toString())
				.setUnitPrice(100)
				.setInventoryCount(100)
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.DONATION)
						.setEntryValue(101)
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
				);	
	}
}
