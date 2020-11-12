package org.sampratistaana;

import static org.sampratistaana.Mainwindow.loadForm;

import java.io.IOException;

import org.sampratistaana.beans.Member;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

@SuppressWarnings("unchecked")
public class MemberController {
	private Label memberNo;
	private TextField name;
	private TextField nickName;
	private TextField address;
	private String membershipType;
	private TextField mobileNo;
	private TextField phoneNo;
	private TextField email;
	private DateCell dateOfBirth;

	@FXML
	private TableView<Member> memberListTable;
		
	public void loadMembers() throws IOException {
		VBox box=loadForm("MemberList");
		loadMembers((TableView<Member>)box.lookup("#memberList"));
	}
	
	public void loadMembers(TableView<Member> memberListTable) {
		for(TableColumn<Member, ?> col:memberListTable.getColumns()) {
			if(!col.getId().equals("action")) {
				col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
			}
		}
		/*
		 * TODO: I dont like this idea of translation one of object to other. We should either use Map (again not good idea) or use separate bean 
		 * which all required attribute. Currently this is causing performance bottleneck.
		 */
//		memberListTable.getItems().clear();
//		for(Member member:new CreditManager().getAllMembers()) {
//			memberListTable.getItems().add(MemberListUI.of(member));
//		}
		memberListTable.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
	}
	
	public VBox loadNewMemberForm() throws IOException {
		return loadForm("NewMemberForm");
	}
	
	public void saveMember() throws IOException{
		
		loadMembers();
	}
}
