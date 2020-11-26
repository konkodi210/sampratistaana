package org.sampratistaana.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.BookSaleUIList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class BookSaleListController extends BaseController {
	
	@FXML private TableView<BookSaleUIList> bookSaleList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeTableColumns(bookSaleList);
		bookSaleList.setItems(FXCollections.observableList(new CreditManager().getBookSaleList()));
		bookSaleList
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		});
	}
	
	public void loadBookSale() throws Exception {
		loadForm("BookSale");
	}
	public void editBookSale() throws Exception{		
		addToCache(BookSaleEditControler.CACHE_KEY,
				new CreditManager()
				.getBookSale(bookSaleList
						.getSelectionModel()
						.getSelectedItem()
						.getLedgerEntryNo()));
		loadBookSale();
	}
	public void deleteBookSale() {}
}
