package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Ledger.TransactionMode;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class DonationListController extends BaseController implements Initializable{
	@FXML private TableView<Donation> donationList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(TableColumn<Donation, ?> col:donationList.getColumns()) {
			col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
		}
		donationList.setItems(FXCollections.observableArrayList(new CreditManager().getAllDonations()));
		donationList
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		});		
	}
	
	public void loadNewDonation() throws IOException {
		Donation member=new Donation()
				.setLedger(
						new Ledger()
						.setEntryDate(System.currentTimeMillis())
						.setModeOfTranscation(TransactionMode.CASH)
						);
		addToCache(DonationEditController.CACHE_KEY, member);
		loadForm("DonationForm");
	}
	
	public void editDonation() throws IOException {
		addToCache(DonationEditController.CACHE_KEY, donationList.getSelectionModel().getSelectedItem());
		loadForm("DonationForm");		
	}
	
	public void deleteDonation() throws IOException {
		//new CreditManager().delete(donationList.getSelectionModel().getSelectedItem());
		loadForm("DonationList");
	}
}
