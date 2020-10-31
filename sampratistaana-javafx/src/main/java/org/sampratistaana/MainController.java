package org.sampratistaana;

import javafx.fxml.FXML;
import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

public class MainController {
	
	@FXML
	private void openNewMembershipForm() throws IOException {
		loadForm("NewMemberForm");		
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
