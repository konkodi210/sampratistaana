package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MemberEditController extends BaseController implements Initializable{
	public static final String CACHE_KEY="MemberEdit";
	@FXML private VBox memberForm;
	@FXML private Label memberNo;
	@FXML private Label entryDate;
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Member member = (Member)getFromCache(CACHE_KEY);
		memberForm.setUserData(member);
		memberNo.setText(String.valueOf(member.getMemberNo()));
		entryDate.setText(formatDate(member.getLedger().getEntryDate()));
		name.setText(member.getName());
		nickName.setText(member.getNickName());
		address.setText(member.getAddress());
		for(Toggle toggle:membership.getToggles()) {			
			if(member.getMembershipType().toString().equals(toggle.getProperties().get("value"))) {
				membership.selectToggle(toggle);
				break;
			}
		}
		mobileNo.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter()));
		mobileNo.setText(member.getMobileNo());
		phoneNo.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter()));
		phoneNo.setText(member.getPhoneNo());		
		email.setText(member.getEmail());
		if(member.getDateOfBirth() >0) {
			LocalDate dob = Instant.ofEpochMilli(member.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDate();
			dateOfBirth.setValue(dob);
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
		//TODO:Description need to be added
		//description.setText(member.getLedger().get);

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
			.setEntryValue(Double.parseDouble(amount.getText()));
		if(dateOfBirth.getValue()!=null) {
			member.setDateOfBirth(dateOfBirth.getValue().toEpochDay());
		}
		new CreditManager().saveMember(member);
		loadMembers();
	}
}