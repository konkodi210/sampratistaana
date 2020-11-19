package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class MemberUITest extends ApplicationTest{

	@Override
	public void start(Stage stage) throws Exception {
		new Mainwindow().start(stage);		
	}

	@Before
	public void setup() {
		clickOn("#credit");
		clickOn("#member");
	}

	@After
	public void tearDown() throws Exception {
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}

	private <T extends Node> T find(String id) {
		return lookup(id).query();
	}

	private String getLabelText(String id) {
		return ((Label)find(id)).getText();
	}

	private void writeToTextFiled(String id, String val) {
		((TextInputControl)find(id)).clear();
		clickOn(id);		
		write(val);
	}

	private void enterMemberDetails(Member memberForUI) {
		writeToTextFiled("#name",memberForUI.getName());
		writeToTextFiled("#nickName",memberForUI.getNickName());
		writeToTextFiled("#address",memberForUI.getAddress());
		clickOn("#yearlyMembership");		
		writeToTextFiled("#mobileNo",memberForUI.getMobileNo());		
		writeToTextFiled("#phoneNo",memberForUI.getPhoneNo());		
		writeToTextFiled("#email",memberForUI.getEmail());
//		LocalDate dob = Instant.ofEpochMilli(memberForUI.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDate();
		((DatePicker)find("#dateOfBirth")).setValue(memberForUI.getDateOfBirth());

		writeToTextFiled("#amount","Invalid Amount");
		clickOn("#mobileNo");
		assertThat("Amount should be always decimal", ((TextField)find("#amount")).getText(),isEmptyOrNullString());
		((TextField)find("#amount")).setText(String.valueOf(memberForUI.getLedger().getEntryValue()));

		clickOn("#paymentOnline");
		writeToTextFiled("#externalTranNo",memberForUI.getLedger().getExternalTranNo());
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

		Member memberForUI=new Member()
				.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows")
				.setAddress("blah house blah post")
				.setMembershipType(MembershipType.YEARLY)
				.setMobileNo("9878432456")
				.setPhoneNo("5345634563")
				.setEmail("abc@gmail.com")
				.setDateOfBirth(LocalDate.now())
				.setLedger(
						new Ledger()
						.setEntryValue(101)
						.setModeOfTranscation(TransactionMode.ONLINE)
						.setExternalTranNo("SomeBank123")
						);	
		enterMemberDetails(memberForUI);

		//TODO: Description is not yet implemented
		writeToTextFiled("#description","Some Description");
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
						);	
		enterMemberDetails(memberForUI);
		clickOn("#memberSave");
		listOfMembers=new CreditManager().getAllMembers();
		member=listOfMembers.get(0);
		matchMemberBean(member,memberForUI);
		
		//test Delete
		((TableView)find("#memberList")).getSelectionModel().select(0);
		int size=listOfMembers.size();
		clickOn("#deleteBtn");
		assertThat("should have one row less after delete", new CreditManager().getAllMembers().size(),equalTo(size-1));
	}
}
