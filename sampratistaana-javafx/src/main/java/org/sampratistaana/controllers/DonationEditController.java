package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class DonationEditController extends BaseController{
	public static final String CACHE_KEY="DonationEdit";
	
	@FXML private VBox donationForm;
	@FXML private Label donationId;
	@FXML private Label entryDate;
	@FXML private TextField name;
	@FXML private TextField nickName;
	@FXML private TextArea address;
	@FXML private TextField pan;
	@FXML private TextField mobileNo;
	@FXML private TextField phoneNo;
	@FXML private TextField email;
	@FXML private DatePicker dateOfBirth;
	@FXML private ToggleGroup paymentType;
	@FXML private TextField amount;
	@FXML private TextField externalTranNo;
	@FXML private TextField description;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Donation donation = (Donation)getFromCache(CACHE_KEY);
		donationForm.setUserData(donation);
		donationId.setText(String.valueOf(donation.getDonationId()));
		entryDate.setText(formatDate(donation.getLedger().getEntryDate()));
		name.setText(donation.getName());
		nickName.setText(donation.getNickName());
		address.setText(donation.getAddress());
		pan.setText(donation.getLedger().getPanNo());
		mobileNo.setText(donation.getMobileNo());
		phoneNo.setText(donation.getPhoneNo());
		email.setText(donation.getEmail());
		if(donation.getDateOfBirth() >0) {
			LocalDate dob = Instant.ofEpochMilli(donation.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDate();
			dateOfBirth.setValue(dob);
		}
		for(Toggle toggle:paymentType.getToggles()) {			
			if(donation.getPaymentType().toString().equals(toggle.getProperties().get("value"))) {
				paymentType.selectToggle(toggle);
				break;
			}
		}
		externalTranNo.setText(donation.getLedger().getExternalTranNo());
		amount.setText(String.valueOf(donation.getLedger().getEntryValue()));
	}

	public void saveDonation() throws IOException{
		Donation donation = (Donation)donationForm.getUserData();
		donation.setName(name.getText())
		.setNickName(nickName.getText())
		.setAddress(address.getText())
		.setMobileNo(mobileNo.getText())
		.setPhoneNo(phoneNo.getText())
		.setEmail(email.getText())
		.getLedger()
			.setEntryCategory(EntryCategory.MEMBER)
			.setEntryType(EntryType.CREDIT)
			.setEntryValue(100)
			.setExternalTranNo(externalTranNo.getText())
			.setModeOfTranscation(TransactionMode.valueOf((String)paymentType.getSelectedToggle().getProperties().get("value")))
			.setEntryValue(Double.parseDouble(amount.getText()));
		if(dateOfBirth.getValue()!=null) {
			donation.setDateOfBirth(dateOfBirth.getValue().toEpochDay());
		}
		new CreditManager().saveDonation(donation);		
		loadForm("DonationList");
	}
	
	public void loadDonations() throws IOException{
		loadForm("DonationList");
	}
}
