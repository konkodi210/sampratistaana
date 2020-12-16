package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.ListOfValues;
import org.sampratistaana.Messages;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.collections.FXCollections;
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
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class MemberEditController extends BaseController{
	public static final String CACHE_KEY="MemberEdit";
	@FXML private VBox memberForm;
	@FXML private Label memberNo;
	@FXML private DatePicker entryDate;
	@FXML private TextField name;
	@FXML private TextField nickName;
	@FXML private TextArea address;
	@FXML private ToggleGroup membership;
	@FXML private TextField mobileNo;
	@FXML private TextField phoneNo;
	@FXML private TextField email;
	@FXML private DatePicker dateOfBirth;
	@FXML private ToggleGroup paymentType;
	@FXML private TextField amount;
	@FXML private TextField externalTranNo;
	@FXML private TextField description;
	@FXML private ComboBox<BankAccount> bankAccount;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Member member = (Member)getFromCache(CACHE_KEY);
		bankAccount.setConverter(new StringConverter<BankAccount>() {
		    Map<String, BankAccount> propertyMap = new HashMap<>();

		    @Override
		    public String toString(BankAccount bank) {
		    	if(bank == null) {
		    		return null;
		    	}
		    	String value = Messages.getMessage(bank.getBankName());
		        propertyMap.put(value, bank);
		        return value;
		    }

		    @Override
		    public BankAccount fromString(String value) {
		        return propertyMap.get(value);
		    }
		});
		bankAccount.setItems(FXCollections.observableArrayList(new ListOfValues().getBankAccountTable()));
		if(member.getLedger().getBankAccount()!=null) {
			bankAccount.setValue(member.getLedger().getBankAccount());
		}else if(bankAccount.getItems().size()>0) {
			bankAccount.setValue(bankAccount.getItems().get(0));
		}
		paymentType.selectedToggleProperty().addListener((obs,old,toggle) -> {
			bankAccount.setVisible(!toggle.getProperties().get("value").equals("CASH"));
		});
		
		memberForm.setUserData(member);
		memberNo.setText(String.valueOf(member.getMemberNo()));
		entryDate.setValue(member.getLedger().getEntryDate());
		name.setText(member.getName());
		nickName.setText(member.getNickName());
		address.setText(member.getAddress());
		for(Toggle toggle:membership.getToggles()) {			
			if(member.getMembershipType().toString().equals(toggle.getProperties().get("value"))) {
				membership.selectToggle(toggle);
				break;
			}
		}
		mobileNo.setText(member.getMobileNo());
		phoneNo.setText(member.getPhoneNo());		
		email.setText(member.getEmail());
		if(member.getDateOfBirth()!=null) {
			dateOfBirth.setValue(member.getDateOfBirth());
		}
		for(Toggle toggle:paymentType.getToggles()) {			
			if(member.getPaymentType().toString().equals(toggle.getProperties().get("value"))) {
				paymentType.selectToggle(toggle);
				break;
			}
		}
		externalTranNo.setText(member.getLedger().getExternalTranNo());
		amount.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter()));
		amount.setText(String.valueOf(member.getLedger().getEntryValue()));		
		description.setText(member.getLedger().getEntryDesc());

	}

	public void loadMembers() throws IOException {
		new MemberListController().loadMembers();
	}

	public void saveMember() throws IOException{
		Member member = (Member)memberForm.getUserData();
		member.setName(name.getText())
		.setNickName(nickName.getText())
		.setAddress(address.getText())
		.setMobileNo(mobileNo.getText())
		.setPhoneNo(phoneNo.getText())
		.setEmail(email.getText())
		.setMembershipType(MembershipType.valueOf((String)membership.getSelectedToggle().getProperties().get("value")))		
		.getLedger()
			.setEntryCategory(EntryCategory.MEMBER)
			.setEntryType(EntryType.CREDIT)
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setExternalTranNo(externalTranNo.getText())
			.setModeOfTranscation(TransactionMode.valueOf((String)paymentType.getSelectedToggle().getProperties().get("value")))
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setEntryDesc(description.getText());
		if(dateOfBirth.getValue()!=null) {
			member.setDateOfBirth(dateOfBirth.getValue());
		}
		new CreditManager().saveMember(member);
		loadMembers();
	}
}
