package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Property;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class DonationEditController extends BaseController{
	public static final String CACHE_KEY="DonationEdit";
	
	@FXML private VBox donationForm;
	@FXML private Label donationId;
	@FXML private DatePicker entryDate;
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
	@FXML private Label depositAccountLabel;
	@FXML private ComboBox<BankAccount> depositAccount;
	@FXML private ComboBox<Property> fundType;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Donation donation = (Donation)getFromCache(CACHE_KEY);
		setComboxItems(depositAccount,lov().getBankAccountTable());
		setComboxItems(fundType,lov().getFundTypes());
		
		if(donation.getLedger().getBankAccount()!=null) {
			depositAccount.setValue(donation.getLedger().getBankAccount());
		}else if(depositAccount.getItems().size()>0) {
			depositAccount.setValue(depositAccount.getItems().get(0));
		}
		if(donation.getFund()==null) {
			fundType.setValue(fundType.getItems().get(0));
		}else {
			fundType.setValue(fundType.getConverter().fromString(donation.getFund()));
		}
		paymentType.selectedToggleProperty().addListener((obs,old,toggle) -> {
			depositAccount.setVisible(!toggle.getProperties().get("value").equals("CASH"));
			depositAccountLabel.setVisible(depositAccount.isVisible());
		});
		
		donationForm.setUserData(donation);
		donationId.setText(String.valueOf(donation.getDonationId()));
		entryDate.setValue(donation.getLedger().getEntryDate());
		name.setText(donation.getName());
		nickName.setText(donation.getNickName());
		address.setText(donation.getAddress());
		pan.setText(donation.getLedger().getPanNo());
		mobileNo.setText(donation.getMobileNo());
		phoneNo.setText(donation.getPhoneNo());
		email.setText(donation.getEmail());
		setComboBoxValue(fundType,donation.getLedger().getFundType());
		if(donation.getDateOfBirth() !=null) {
			dateOfBirth.setValue(donation.getDateOfBirth() );
		}
		for(Toggle toggle:paymentType.getToggles()) {			
			if(donation.getPaymentType().toString().equals(toggle.getProperties().get("value"))) {
				paymentType.selectToggle(toggle);
				break;
			}
		}
		externalTranNo.setText(donation.getLedger().getExternalTranNo());
		amount.setTextFormatter(new TextFormatter<Double>(getCurrnecyConvertor()));
		amount.setText(String.valueOf(donation.getLedger().getEntryValue()));		
		description.setText(donation.getLedger().getEntryDesc());
	}

	public void saveDonation() throws IOException{
		Donation donation = (Donation)donationForm.getUserData();
		TransactionMode tranMode = TransactionMode.valueOf(getToggleValue(paymentType));
		donation.setName(name.getText())
		.setNickName(nickName.getText())
		.setAddress(address.getText())
		.setMobileNo(mobileNo.getText())
		.setPhoneNo(phoneNo.getText())
		.setEmail(email.getText())
		.getLedger()
			.setEntryCategory(EntryCategory.MEMBER)
			.setEntryType(EntryType.CREDIT)
			.setExternalTranNo(externalTranNo.getText())
			.setModeOfTranscation(tranMode)
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setPanNo(pan.getText())
			.setFundType(fundType.getValue().getPropertyValue())
			.setBankAccount(tranMode == TransactionMode.CASH?null:depositAccount.getValue())
			.setEntryDesc(description.getText());
		if(dateOfBirth.getValue()!=null) {
			donation.setDateOfBirth(dateOfBirth.getValue());
		}
		new CreditManager().saveDonation(donation);		
		loadForm("DonationList");
	}
	
	public void loadDonations() throws IOException{
		loadForm("DonationList");
	}
}
