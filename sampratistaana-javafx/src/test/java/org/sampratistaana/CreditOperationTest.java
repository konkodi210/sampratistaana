package org.sampratistaana;

import org.junit.Test;
import org.sampratistaana.beans.Member;

public class CreditOperationTest {

	@Test
	public void testSaveMember() throws Exception {
		Member member=(Member)new Member()
				.setName("ABC")
				.setLedgerEntryNo(1)
				.setEntryType("C")
				.setEntryCategory("MEMBER")
				.setEntryDate(System.currentTimeMillis())
				.setModeOfTranscation("CASH");				
		new CreditOperationManager().saveMember(member);
	}
}
