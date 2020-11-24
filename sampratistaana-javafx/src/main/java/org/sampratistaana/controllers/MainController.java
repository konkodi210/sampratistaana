package org.sampratistaana.controllers;

import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainController {
		
	@FXML
	private void openNewMembershipForm() throws IOException {
		loadForm("MemberList");
		//new MemberListController().loadMembers((TableView<Member>)box.lookup("#memberList"));
	}
	
	@FXML
	private void openDonationForm() throws IOException{
		loadForm("DonationList");
	}
	
	@FXML
	private void openBookSaleForm() throws IOException{		
		loadForm("BookSaleList");
	}

}
