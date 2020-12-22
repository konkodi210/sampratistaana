package org.sampratistaana.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.sampratistaana.beans.Property;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class LovAdminController extends BaseController{
	@FXML private ComboBox<String> lovComboBox;
	@FXML private Button saveBtn;
	@FXML private Button deleteBtn;
	@FXML private TableView<Property> lovTable;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lovComboBox.setItems(FXCollections.observableArrayList(lov().getLovs()));
		
		if(lovComboBox.getItems().size()>0) {
			
		}
	}
	
}
