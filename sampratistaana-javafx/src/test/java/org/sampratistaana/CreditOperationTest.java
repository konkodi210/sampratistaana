package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.Test;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Member;

public class CreditOperationTest {

	private Member createMember() {
		return new Member()
				.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows")
				.setAddress("blah house\r\n blah post")
				.setMembershipType("LIFE")
				.setMobileNo("12345454")
				.setPhoneNo("23434234234")
				.setEmail("abc@gmail.com")
				.setDateOfBirth(System.currentTimeMillis())
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.MEMBER)
						.setEntryValue(101)
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation("CASH")
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
				);	
	}
	
	@Test
	public void testSaveMember() throws Exception {
		Member member=createMember();				
		new CreditOperationManager().saveMember(member);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)member.getLedger().getEntryNo(),greaterThan(0));
		assertThat("Member id should auto generated", (int)member.getMemberNo(),greaterThan(0));
		
	}

	@Test
	public void testGetMember() throws Exception {
		Member member=createMember();				
		new CreditOperationManager().saveMember(member);
		Member memberFromDb = new CreditOperationManager().getMember(member.getMemberNo());
		assertThat("Member record must be found", memberFromDb,notNullValue());
		assertThat("A ledger record must be assosiated with that", memberFromDb.getLedger(),notNullValue());
		assertThat("Both Objects should match", member.toString(),equalTo(memberFromDb.toString()));

	}
	
	private Donation createDonation() {
		return new Donation()
				.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows")
				.setAddress("blah house\r\n blah post")
				.setMobileNo("12345454")
				.setPhoneNo("23434234234")
				.setEmail("abc@gmail.com")
				.setDateOfBirth(System.currentTimeMillis())
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.DONATION)
						.setEntryValue(101)
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation("CASH")
						.setExternalTranNo("SomeBank123")
						.setPanNo("ABC64246")
				);	
	}
	
	@Test
	public void testSaveDonation() throws Exception {
		Donation donation=createDonation();				
		new CreditOperationManager().saveDonation(donation);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)donation.getLedger().getEntryNo(),greaterThan(0));
		assertThat("Member id should auto generated", (int)donation.getDonationId(),greaterThan(0));
		
	}

	@Test
	public void testGetDonation() throws Exception {
		Donation donation=createDonation();				
		new CreditOperationManager().saveDonation(donation);
		Donation donationFromDb = new CreditOperationManager().getDonation(donation.getDonationId());
		assertThat("Member record must be found", donationFromDb,notNullValue());
		assertThat("A ledger record must be assosiated with that", donationFromDb.getLedger(),notNullValue());
		assertThat("Both Objects should match", donation.toString(),equalTo(donationFromDb.toString()));

	}

}
