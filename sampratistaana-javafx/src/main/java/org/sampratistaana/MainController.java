package org.sampratistaana;

import javafx.fxml.FXML;
import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

public class MainController {
	
	@FXML
	private void openNewMembership() throws IOException {
		loadForm("NewMemberForm");
		
	}

}
