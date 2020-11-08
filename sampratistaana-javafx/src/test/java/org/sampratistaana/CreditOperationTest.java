package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.UUID;

import org.junit.Test;
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
				.setEntryType("C")
				.setEntryCategory("MEMBER")
				.setEntryValue(101)
				.setEntryDate(System.currentTimeMillis())
				.setModeOfTranscation("CASH")
				.setExternalTranNo("SomeBank123")
				.setPanNo("ABC64246");	
	}
	@Test
	public void testSaveMember() throws Exception {
		Member member=createMember();				
		new CreditOperationManager().saveMember(member);
		//for some reason it does not like long. hence typecasting to int. Since id is will be small, there will not be any overflow 
		assertThat("Ledger entry no should be generated", (int)member.getEntryNo(),greaterThan(0));
		assertThat("Member id should auto generated", (int)member.getMemberNo(),greaterThan(0));
	}
	
	@Test
	public void testGetMember() throws Exception {
		throw new NullPointerException("Test not implemeneted");
	}
}
