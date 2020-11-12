package org.sampratistaana;

import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

import org.sampratistaana.beans.MemberListUI;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

@SuppressWarnings("unchecked")
public class MainController {
		
	@FXML
	private void openNewMembershipForm() throws IOException {
		VBox box=loadForm("MemberList");
		new MemberController().loadMembers((TableView<MemberListUI>)box.lookup("#memberList"));
	}
	
	@FXML
	private void openDonationForm() throws IOException{
		loadForm("DonationForm");
	}
	
	@FXML
	private void openBookSaleForm() throws IOException{
		loadForm("BookSale");
	}

}
