package org.sampratistaana;

import static org.sampratistaana.Mainwindow.loadForm;
import static org.sampratistaana.Messages.formatDate;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Member.MembershipType;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
		//TODO:Membership fee is not covered
		member.setName(getString(box, "name"))
		.setNickName(getString(box, "nickName"))
		.setAddress(getString(box, "address"))
		.setMembershipType(MembershipType.valueOf(getString(box, "membershipType")))
		.setMobileNo(getString(box, "mobileNo"))
		.setPhoneNo(getString(box, "phoneNo"))
		.setEmail(getString(box, "email"))		
		.setLedger(new Ledger()
				.setEntryType(EntryType.CREDIT)
				.setEntryCategory(EntryCategory.MEMBER)
				.setEntryDate(System.currentTimeMillis())
				.setModeOfTranscation(TransactionMode.valueOf(getString(box, "paymentType")))
				);
		
		loadMembers();
	}

	public VBox loadMember(Member member) throws IOException{
		VBox box=loadForm(MEMBER_FORM);
		box.setUserData(member);

		((Label)box.lookup("#memberNo")).setText(String.valueOf(member.getMemberNo()));
		((Label)box.lookup("#entryDate")).setText(formatDate(member.getLedger().getEntryDate()));
		((TextField)box.lookup("#name")).setText(member.getName());
		((TextField)box.lookup("#nickName")).setText(member.getNickName());
		((TextArea)box.lookup("#address")).setText(member.getAddress());
		//((ToggleGroup)box.lookup("#membershipType")).selectedToggleProperty()
		((TextField)box.lookup("#mobileNo")).setText(member.getMobileNo());
		((TextField)box.lookup("#phoneNo")).setText(member.getMobileNo());
		((TextField)box.lookup("#email")).setText(member.getEmail());
		if(member.getDateOfBirth() >0) {
			LocalDate dob = Instant.ofEpochMilli(member.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDate();
			((DatePicker)box.lookup("#dateOfBirth")).setValue(dob);
		}		
		//((TextField)box.lookup("#externalTranNo")).setText(member.getLedger().getExternalTranNo());
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
