package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MemberStatus;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

public class MemberEditController extends BaseController{
	public static final String CACHE_KEY="MemberEdit";
	@FXML private VBox memberForm;
	@FXML private Label memberNo;
	@FXML private DatePicker entryDate;
	@FXML private TextField name;
	@FXML private TextField nickName;
	@FXML private TextArea address;
	@FXML private TextField pan;
	@FXML private ToggleGroup membership;
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
	@FXML private ToggleGroup memberStatus;
	@FXML private TextField aadhar;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Member member = (Member)getFromCache(CACHE_KEY);
		setComboxItems(depositAccount,lov().getBankAccountTable());

		if(member.getLedger().getBankAccount()!=null) {
			depositAccount.setValue(member.getLedger().getBankAccount());
		}else if(depositAccount.getItems().size()>0) {
			depositAccount.setValue(depositAccount.getItems().get(0));
		}
		
		paymentType.selectedToggleProperty().addListener((obs,old,toggle) -> {
			depositAccount.setVisible(!toggle.getProperties().get("value").equals("CASH"));
			depositAccountLabel.setVisible(depositAccount.isVisible());
			externalTranNo.setDisable(!depositAccount.isVisible());
		});
		
		memberForm.setUserData(member);
		memberNo.setText(String.valueOf(member.getMemberNo()));
		entryDate.setValue(member.getLedger().getEntryDate());
		name.setText(member.getName());
		nickName.setText(member.getNickName());
		address.setText(member.getAddress());
		setToggleValue(membership, member.getMembershipType());

		mobileNo.setText(member.getMobileNo());
		phoneNo.setText(member.getPhoneNo());		
		email.setText(member.getEmail());
		if(member.getDateOfBirth()!=null) {
			dateOfBirth.setValue(member.getDateOfBirth());
		}
		setToggleValue(paymentType, member.getPaymentType());

		externalTranNo.setText(member.getLedger().getExternalTranNo());
		amount.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter()));
		amount.setText(String.valueOf(member.getLedger().getEntryValue()));		
		description.setText(member.getLedger().getEntryDesc());
		pan.setText(member.getLedger().getPanNo());
		setToggleValue(memberStatus, member.getMemberStatus());
		aadhar.setText(member.getAadharNo());
	}

	public void loadMembers() throws IOException {
		new MemberListController().loadMembers();
	}

	public void saveMember() throws IOException{
		Member member = (Member)memberForm.getUserData();
		TransactionMode tranMode = TransactionMode.valueOf(getToggleValue(paymentType));
		member.setName(name.getText())
		.setNickName(nickName.getText())
		.setAddress(address.getText())
		.setMobileNo(mobileNo.getText())
		.setPhoneNo(phoneNo.getText())
		.setEmail(email.getText())
		.setMembershipType(MembershipType.valueOf(getToggleValue(membership)))
		.setMemberStatus(MemberStatus.valueOf(getToggleValue(memberStatus)))
		.setAadharNo(aadhar.getText())
		.getLedger()
			.setEntryCategory(EntryCategory.MEMBER)
			.setEntryType(EntryType.CREDIT)
			.setEntryDate(entryDate.getValue())
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setBankAccount(tranMode == TransactionMode.CASH?null:depositAccount.getValue())
			.setExternalTranNo(tranMode == TransactionMode.CASH?null:externalTranNo.getText())
			.setModeOfTranscation(tranMode)
			.setPanNo(pan.getText())			
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setEntryDesc(description.getText());
		if(dateOfBirth.getValue()!=null) {
			member.setDateOfBirth(dateOfBirth.getValue());
		}
		new CreditManager().saveMember(member);
		loadMembers();
	}
}
