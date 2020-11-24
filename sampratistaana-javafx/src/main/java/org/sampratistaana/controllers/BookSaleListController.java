package org.sampratistaana.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class BookSaleListController extends BaseController {
	
	@FXML private TableView<Object> bookSaleList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void loadBookSale() throws Exception {
		loadForm("BookSale");
	}
	public void editBookSale() {}
	public void deleteBookSale() {}
}
