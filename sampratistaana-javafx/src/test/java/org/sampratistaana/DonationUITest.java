package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.sampratistaana.TestUtils.createDonation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@SuppressWarnings("rawtypes")
public class DonationUITest extends BaseApplicationTest {
	@Before
	public void setup() {
		clickOn("#creditMenu");
		clickOn("#donationMenu");
	}
	
	private void enterDonationDetails(Donation donationUI) {
		writeToTextFiled("#name",donationUI.getName());
		writeToTextFiled("#nickName",donationUI.getNickName());
		writeToTextFiled("#address",donationUI.getAddress());			
		writeToTextFiled("#mobileNo",donationUI.getMobileNo());		
		writeToTextFiled("#phoneNo",donationUI.getPhoneNo());		
		writeToTextFiled("#email",donationUI.getEmail());
//		writeToTextFiled("#amount","Invalid Amount");
		((DatePicker)find("#dateOfBirth")).setValue(donationUI.getDateOfBirth());
		clickOn("#mobileNo");
//		assertThat("Amount should be always decimal", ((TextField)find("#amount")).getText(),isEmptyOrNullString());
		((TextField)find("#amount")).setText(String.valueOf(donationUI.getLedger().getEntryValue()));

		clickOn("#paymentOnline");
		writeToTextFiled("#externalTranNo",donationUI.getLedger().getExternalTranNo());
		writeToTextFiled("#pan", donationUI.getLedger().getPanNo());
		writeToTextFiled("#description", donationUI.getLedger().getEntryDesc());
//		ComboBox<Property> fundType=find("#fundType");
//		fundType.setValue(fundType.getConverter().fromString(donationUI.getFund()));
	}
	
	private void matchDonationBean(Donation donation, Donation donationUI) {
		assertThat(donation.getName(), equalTo(donationUI.getName()));
		assertThat(donation.getNickName(), equalTo(donationUI.getNickName()));
		assertThat(donation.getAddress(), equalToIgnoringWhiteSpace(donationUI.getAddress()));
		assertThat(donation.getMobileNo(), equalTo(donationUI.getMobileNo()));
		assertThat(donation.getPhoneNo(), equalTo(donationUI.getPhoneNo()));
		assertThat(donation.getEmail(), equalTo(donationUI.getEmail()));
		assertThat(donation.getDateOfBirth(), equalTo(donationUI.getDateOfBirth()));
		assertThat(donation.getLedger().getEntryValue(), equalTo(donationUI.getLedger().getEntryValue()));
		assertThat(donation.getLedger().getExternalTranNo(), equalTo(donationUI.getLedger().getExternalTranNo()));
		assertThat(donation.getLedger().getPanNo(), equalTo(donationUI.getLedger().getPanNo()));
		assertThat(donation.getLedger().getEntryDesc(), equalTo(donation.getLedger().getEntryDesc()));
		assertThat(donation.getFund(), equalTo(donationUI.getFund()));
	}
	
	@Test
	public void testDonationScreen() {
		List<Donation> oldlistOfDonations=new CreditManager().getAllDonations();

		//New Donation testing
		clickOn("#donationNew");	

		//test cancel button
		clickOn("#donationCancelBtn");
		assertThat("when cancel is clicked, page should navigate back to listing page",
				find("#donationNew"),notNullValue());

		//new Donation
		clickOn("#donationNew");
		assertThat("For new donations, id is not assigned",getLabelText("#donationId"), equalTo("0"));
		assertThat("Date is set to today's date", Messages.formatDate(((DatePicker)find("#entryDate")).getValue()),equalTo(Messages.formatDate(LocalDate.now())));

		Donation donationUI=createDonation();
		enterDonationDetails(donationUI);

		//TODO: Description is not yet implemented
		writeToTextFiled("#description","Some Description");		
		clickOn("#saveDonationBtn");
		assertThat("when save is clicked, page should navigate back to listing page",
				find("#donationNew"),notNullValue());
		List<Donation> listOfDonation=new CreditManager().getAllDonations();
		assertThat("New Donation must be saved in DB", listOfDonation.size()-oldlistOfDonations.size(),equalTo(1));

		Donation donation=listOfDonation.get(0);
		matchDonationBean(donation,donationUI);


		//validating the listing screen
		assertThat("Edit button should be disabled by default", ((Button)find("#editBtn")).isDisabled(),equalTo(true));
		assertThat("Delete button should be disabled by default", ((Button)find("#deleteBtn")).isDisabled(),equalTo(true));

		((TableView)find("#donationList")).getSelectionModel().select(0);
		

		//validating the listing screen
		assertThat("Edit button should be enabled by when row selected", ((Button)find("#editBtn")).isDisabled(),equalTo(false));
		assertThat("Delete button should be enabled by when row selected", ((Button)find("#deleteBtn")).isDisabled(),equalTo(false));

		//Edit Donation
		clickOn("#editBtn");
		assertThat("For new Donation, id is not assigned",getLabelText("#donationId"), not(equalTo("0")));
		donationUI.setName(UUID.randomUUID().toString())
				.setNickName("GodKnows11")
				.setAddress("blah house blah post111")
				.setMobileNo("234324564536")
				.setPhoneNo("4565465464")
				.setEmail("abc1234@gmail.com")
				.setLedger(
						new Ledger()
						.setEntryValue(858)
						.setModeOfTranscation(TransactionMode.ONLINE)
						.setExternalTranNo("SomeBank123111")
						.setPanNo("SomeNewPAN")
						.setEntryDesc("New Description")
						.setBankAccount(TestUtils.getBankAccount())
						.setFundType(new ListOfValues().getFundTypes().get(0).getPropertyValue())
						);	
		enterDonationDetails(donationUI);
		clickOn("#saveDonationBtn");
		listOfDonation=new CreditManager().getAllDonations();		
		matchDonationBean(listOfDonation.get(0),donationUI);
		
		//test Delete
		((TableView)find("#donationList")).getSelectionModel().select(0);
		performConfirmedDelete("#deleteBtn");
		assertThat("should have one row less after delete", new CreditManager().getAllDonations().size(),equalTo(listOfDonation.size()-1));		
	}
}
