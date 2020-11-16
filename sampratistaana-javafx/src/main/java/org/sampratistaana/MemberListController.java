package org.sampratistaana;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class MemberListController extends BaseController implements Initializable{
	private static final String MEMBER_FORM="MemberForm";
	
	@FXML private TableView<Member> memberList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(TableColumn<Member, ?> col:memberList.getColumns()) {
			if(!col.getId().equals("action")) {
				col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
			}
		}
		memberList.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
		memberList
			.getSelectionModel()
			.selectedItemProperty()
			.addListener((obs,oldVal,newVal) -> handleRowSelection());
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
	
	private void handleRowSelection() {
		editBtn.setDisable(false);
		deleteBtn.setDisable(false);
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
