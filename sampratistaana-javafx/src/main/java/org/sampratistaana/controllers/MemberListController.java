package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class MemberListController extends BaseController{
	private static final String MEMBER_FORM="MemberForm";

	@FXML private TableView<Member> memberList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeTableColumns(memberList);
		
		memberList.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
		memberList
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		});
	}

	public void loadMembers() throws IOException {
		loadForm("MemberList");	
	}

	public VBox loadNewMemberForm() throws IOException {
		Member member=new Member()
				.setLedger(
						new Ledger()
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation(TransactionMode.CASH)
						);
		addToCache(MemberEditController.CACHE_KEY, member);
		VBox box= (VBox)loadForm(MEMBER_FORM);
		return box;
	}

	public void deleteMember() throws IOException{
		new CreditManager().deleteMember(memberList.getSelectionModel().getSelectedItem());
		loadMembers();
	}

	public void editMember() throws IOException{
		addToCache(MemberEditController.CACHE_KEY, memberList.getSelectionModel().getSelectedItem());
		loadForm(MEMBER_FORM);
	}
}
