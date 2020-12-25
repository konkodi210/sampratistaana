package org.sampratistaana;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.BookSale;
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

	public static BankAccount getBankAccount() {
		return new ListOfValues().getBankAccountTable().get(0);
	}

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
						.setEntryDate(LocalDate.now())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
						.setBankAccount(getBankAccount())
						.setFundType(new ListOfValues().getFundTypes().get(0).getPropertyValue())
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
						.setEntryDate(LocalDate.now())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
						.setFundType(new ListOfValues().getFundTypes().get(0).getPropertyValue())
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
						.setEntryDate(LocalDate.now())
						.setModeOfTranscation(TransactionMode.CASH)
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
						.setEntryDesc("Description123")
						.setFundType(new ListOfValues().getFundTypes().get(0).getPropertyValue())
						);	
	}

	public static BookSale createBookSale() {
		Inventory inventory=createInventory();		
		return new BookSale()
				.setInventory(inventory)
				.setCustomerName("Good Hearted Man")
				.setUnitCount(5)
				.setLedger(new Ledger()
						.setModeOfTranscation(TransactionMode.CASH)
						);
	}

	/**
	 * For testing, we may need to read attributes which are private. Hence use brute force (Reflection) to read values
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object getProperty(Object obj,String property) {
		try {
			Field field = obj.getClass().getDeclaredField(property);
			field.setAccessible(true);
			return field.get(obj);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
