package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.sampratistaana.TestUtils.createMember;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@SuppressWarnings("rawtypes")
public class MemberUITest extends BaseApplicationTest{

	@Before
	public void setup() {
		clickOn("#creditMenu");
		clickOn("#memberMenu");
	}

	private void enterMemberDetails(Member memberForUI) {
		writeToTextFiled("#name",memberForUI.getName());
		writeToTextFiled("#nickName",memberForUI.getNickName());
		writeToTextFiled("#address",memberForUI.getAddress());
		clickOn("#yearlyMembership");		
		writeToTextFiled("#mobileNo",memberForUI.getMobileNo());		
		writeToTextFiled("#phoneNo",memberForUI.getPhoneNo());		
		writeToTextFiled("#email",memberForUI.getEmail());
		((DatePicker)find("#dateOfBirth")).setValue(memberForUI.getDateOfBirth());

		writeToTextFiled("#amount","Invalid Amount");
		clickOn("#mobileNo");
		assertThat("Amount should be always decimal", ((TextField)find("#amount")).getText(),isEmptyOrNullString());
		((TextField)find("#amount")).setText(String.valueOf(memberForUI.getLedger().getEntryValue()));

		clickOn("#paymentOnline");
		writeToTextFiled("#externalTranNo",memberForUI.getLedger().getExternalTranNo());
		writeToTextFiled("#description", memberForUI.getLedger().getEntryDesc());
	}

	private void matchMemberBean(Member member, Member memberForUI) {
		assertThat(member.getName(), equalTo(memberForUI.getName()));
		assertThat(member.getNickName(), equalTo(memberForUI.getNickName()));
		assertThat(member.getAddress(), equalToIgnoringWhiteSpace(memberForUI.getAddress()));
		assertThat(member.getMobileNo(), equalTo(memberForUI.getMobileNo()));
		assertThat(member.getPhoneNo(), equalTo(memberForUI.getPhoneNo()));
		assertThat(member.getEmail(), equalTo(memberForUI.getEmail()));
		assertThat(member.getDateOfBirth(), equalTo(memberForUI.getDateOfBirth()));
		assertThat(member.getLedger().getEntryValue(), equalTo(memberForUI.getLedger().getEntryValue()));
		assertThat(member.getLedger().getExternalTranNo(), equalTo(memberForUI.getLedger().getExternalTranNo()));
		assertThat(member.getLedger().getEntryDesc(), equalTo(memberForUI.getLedger().getEntryDesc()));
	}

	@Test
	public void testMemberScreen() {
		/*
		 * Ideally for each test scenario we need to have separate testcase. In our case, we have test data dependency.
		 * For example, in order to perform edit member, we need to create new member first. Hence, to avoid duplication of code
		 * we are putting all test steps into single testcase
		 */

		//get all avaliable members which is already in the DB
		List<Member> oldlistOfMembers=new CreditManager().getAllMembers();

		//New Member testing
		clickOn("#newMember");	

		//test cancel button
		clickOn("#memberCancel");
		assertThat("when cancel is clicked, page should navigate back to listing page",
				find("#newMember"),notNullValue());

		//new Member
		clickOn("#newMember");
		assertThat("For new members, memeber id is not assigned",getLabelText("#memberNo"), equalTo("0"));
		assertThat("Date is set to today's date", getLabelText("#entryDate"),equalTo(Messages.formatDate(System.currentTimeMillis())));

		Member memberForUI=createMember();
		enterMemberDetails(memberForUI);

		clickOn("#memberSave");

		assertThat("when save is clicked, page should navigate back to listing page",
				find("#newMember"),notNullValue());

		List<Member> listOfMembers=new CreditManager().getAllMembers();
		assertThat("New member must be saved in DB", listOfMembers.size()-oldlistOfMembers.size(),equalTo(1));

		Member member=listOfMembers.get(0);
		matchMemberBean(member,memberForUI);


		//validating the listing screen
		assertThat("Edit button should be disabled by default", ((Button)find("#editBtn")).isDisabled(),equalTo(true));
		assertThat("Delete button should be disabled by default", ((Button)find("#deleteBtn")).isDisabled(),equalTo(true));

		((TableView)find("#memberList")).getSelectionModel().select(0);
		

		//validating the listing screen
		assertThat("Edit button should be enabled by when row selected", ((Button)find("#editBtn")).isDisabled(),equalTo(false));
		assertThat("Delete button should be enabled by when row selected", ((Button)find("#deleteBtn")).isDisabled(),equalTo(false));

		//Edit Member
		clickOn("#editBtn");
		assertThat("For new members, memeber id is not assigned",getLabelText("#memberNo"), not(equalTo("0")));
		memberForUI.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows11")
				.setAddress("blah house blah post111")
				.setMembershipType(MembershipType.YEARLY)
				.setMobileNo("234324564536")
				.setPhoneNo("4565465464")
				.setEmail("abc1234@gmail.com")
				.setDateOfBirth(LocalDate.now())
				.setLedger(
						new Ledger()
						.setEntryValue(858)
						.setModeOfTranscation(TransactionMode.ONLINE)
						.setExternalTranNo("SomeBank123111")
						.setEntryDesc("Some new description")
						);	
		enterMemberDetails(memberForUI);
		clickOn("#memberSave");
		listOfMembers=new CreditManager().getAllMembers();		
		matchMemberBean(listOfMembers.get(0),memberForUI);
		
		//test Delete
		((TableView)find("#memberList")).getSelectionModel().select(0);
		clickOn("#deleteBtn");
		assertThat("should have one row less after delete", new CreditManager().getAllMembers().size(),equalTo(listOfMembers.size()-1));
	}
}
