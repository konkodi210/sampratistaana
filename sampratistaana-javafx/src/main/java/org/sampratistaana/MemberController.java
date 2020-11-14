package org.sampratistaana;

import static org.sampratistaana.Mainwindow.loadForm;
import static org.sampratistaana.Messages.formatDate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Member;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@SuppressWarnings("unchecked")
public class MemberController {
	private static final String MEMBER_FORM="MemberForm";

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
		memberListTable.setItems(FXCollections.observableArrayList(new CreditManager().getAllMembers()));
	}

	public VBox loadNewMemberForm() throws IOException {
		return loadMember(new Member().setLedger(new Ledger().setEntryDate(System.currentTimeMillis())));
	}

	public void saveMember() throws IOException{
		VBox box=(VBox)Mainwindow.getScene().lookup("#"+MEMBER_FORM);
		Member member=null;
		if(box.getUserData()!=null) {
			member=(Member)box.getUserData();
		}else {
			member=new Member();
		}
		member.setName(getString(box, "name"))
			.setNickName(getString(box, "nickName"));
		loadMembers();
	}

	public VBox loadMember(Member member) throws IOException{
		VBox box=loadForm(MEMBER_FORM);
		box.setUserData(member);
		((Label)box.lookup("#memberNo")).setText(String.valueOf(member.getMemberNo()));
		((Label)box.lookup("#entryDate")).setText(formatDate(member.getLedger().getEntryDate()));
		((TextField)box.lookup("#name")).setText(member.getName());
		((TextField)box.lookup("#nickName")).setText(member.getNickName());
		((TextField)box.lookup("#address")).setText(member.getAddress());
		((TextField)box.lookup("#membershipType")).setText(member.getMembershipType());
		((TextField)box.lookup("#mobileNo")).setText(member.getMobileNo());
		((TextField)box.lookup("#phoneNo")).setText(member.getMobileNo());
		((TextField)box.lookup("#email")).setText(member.getEmail());
		((TextField)box.lookup("#dateOfBirth")).setText(formatDate(member.getDateOfBirth()));
		((TextField)box.lookup("#externalTranNo")).setText(member.getLedger().getExternalTranNo());
		return box;
	}
	
	private String getString(VBox box,String id) {
		Node node=box.lookup("#"+id);
		if(node instanceof Label) {
			return ((Label)node).getText();
		}else if(node instanceof Text) {
			return ((Text)node).getText();
		}else {
			throw new RuntimeException("This instance is not implemeneted "+node.getClass());
		}
	}
}
