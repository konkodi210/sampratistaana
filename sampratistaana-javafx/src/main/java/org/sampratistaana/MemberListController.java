package org.sampratistaana;

import java.io.IOException;

import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

@SuppressWarnings("unchecked")
public class MemberListController extends BaseController{
	private static final String MEMBER_FORM="MemberForm";
	
	@FXML
	private ToggleGroup membershipToggle;

	public void loadMembers() throws IOException {
		VBox box=(VBox)loadForm("MemberList");
		loadMembers((TableView<Member>)box.lookup("#memberList"));
	}

	public void loadMembers(TableView<Member> memberListTable) {
		for(TableColumn<Member, ?> col:memberListTable.getColumns()) {
			if(!col.getId().equals("action")) {
				col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
			}
		}
		memberListTable.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
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
}
