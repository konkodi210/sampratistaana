package org.sampratistaana;

import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.MemberListUI;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class MemberController {

	@FXML
	private TableView<MemberListUI> memberListTable;
		
	public void loadMembers() throws IOException {
		VBox box=loadForm("MemberList");
		loadMembers((TableView<MemberListUI>)box.lookup("#memberList"));
	}
	
	public void loadMembers(TableView<MemberListUI> memberListTable) {
		for(TableColumn<MemberListUI, ?> col:memberListTable.getColumns()) {
			col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
		}
		/*
		 * TODO: I dont like this idea of translation one of object to other. We should either use Map (again not good idea) or use separate bean 
		 * which all required attribute. Currently this is causing performance bottleneck.
		 */
		memberListTable.getItems().clear();
		for(Member member:new CreditManager().getAllMembers()) {
			memberListTable.getItems().add(MemberListUI.of(member));
		}
	}
	
	public VBox loadNewMemberForm() throws IOException {
		return loadForm("NewMemberForm");
	}
}
