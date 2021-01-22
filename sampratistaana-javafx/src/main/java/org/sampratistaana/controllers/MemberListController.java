package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.ListOfValues;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MemberStatus;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class MemberListController extends BaseController{
	private static final String MEMBER_FORM="MemberForm";

	@FXML private TableView<Member> memberList;
	@FXML private Button editBtn;
	@FXML private Button renewBtn;
	@FXML private Button deleteBtn;
	@FXML private Button addDonation;
	@FXML private Button bookSale;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeTableColumns(memberList);

		//memberList.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
		setTableItems(memberList, new CreditManager().getAllMembers());
		memberList
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
			addDonation.setDisable(false);
			bookSale.setDisable(false);
			renewBtn.setDisable(!(newVal.getMemberStatus()==MemberStatus.EXPIRED && newVal.getMembershipType()==MembershipType.YEARLY));		
		});
	}

	public void loadMembers() throws IOException {
		loadForm("MemberList");	
	}

	public VBox loadNewMemberForm() throws IOException {
		Member member=new Member()
				.setLedger(
						new Ledger()
						.setEntryDate(LocalDate.now())
						.setModeOfTranscation(TransactionMode.CASH)
						);
		addToCache(MemberEditController.CACHE_KEY, member);
		VBox box= (VBox)loadForm(MEMBER_FORM);
		return box;
	}

	public void deleteMember() throws IOException{
		if(showDeleteConfirmation()) {
			new CreditManager().deleteMember(memberList.getSelectionModel().getSelectedItem());
			loadMembers();
		}
	}

	public void editMember() throws IOException{
		addToCache(MemberEditController.CACHE_KEY, memberList.getSelectionModel().getSelectedItem());
		loadForm(MEMBER_FORM);
	}

	public void renewMember() throws IOException{
		Member member = memberList.getSelectionModel().getSelectedItem();
		Member renewMember=member.setMemberNo(0);
		renewMember.getLedger().setEntryNo(0);
		addToCache(MemberEditController.CACHE_KEY,renewMember);
		loadForm(MEMBER_FORM);
	}

	@FXML
	public void addNewDonation() throws IOException{
		Member member = memberList.getSelectionModel().getSelectedItem();
		addToCache(DonationEditController.CACHE_KEY,
				new Donation()
				.setName(member.getName())
				.setNickName(member.getNickName())
				.setAddress(member.getAddress())
				.setMobileNo(member.getMobileNo())
				.setPhoneNo(member.getPhoneNo())
				.setEmail(member.getEmail())
				.setDateOfBirth(member.getDateOfBirth())
				.setMember(member)
				.setLedger(
						new Ledger()
						.setEntryType(EntryType.CREDIT)
						.setEntryCategory(EntryCategory.DONATION)
						.setPanNo(member.getLedger().getPanNo())
						.setModeOfTranscation(member.getLedger().getModeOfTranscation())
						.setEntryDate(LocalDate.now())
						.setFundType(new ListOfValues().getFundTypes().get(0).getPropertyValue())
						)
				);
		loadForm("DonationForm");
	}

	@FXML
	public void openBookSale() throws IOException{
		addToCache(BookSaleEditControler.CACHE_KEY, memberList.getSelectionModel().getSelectedItem());
		new BookSaleListController().loadBookSale();
	}
}
