package org.sampratistaana.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.beans.Property;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class LovAdminController extends BaseController{
	@FXML private ComboBox<LovProp> lovComboBox;
	@FXML private Button addBtn;
	@FXML private Button saveBtn;
	@FXML private Button deleteBtn;
	@FXML private TableView<Property> lovTable;
	
	@Override
	@SuppressWarnings({"unchecked","rawtypes"})
	public void initialize(URL location, ResourceBundle resources) {		
		lovTable.getColumns().forEach((TableColumn col)->{
			if(col.getId().equals("propertyValue")) {
				col.setCellFactory(TextFieldTableCell.forTableColumn()); 
			}
			col.setCellValueFactory(new PropertyValueFactory<>(col.getId()));
		});
		lovComboBox.setItems(
				FXCollections.observableArrayList(
						stream(lov().getLovs())
						.map(arr -> new LovProp(arr))
						.collect(Collectors.toList())));
		
		lovComboBox.valueProperty().addListener((obs,oldVal,newVal)->{
			saveBtn.setDisable(false);
			deleteBtn.setDisable(false);
			addBtn.setDisable(false);
			System.out.printf("Value CHange %s=%s\n",newVal.propertyName,newVal.propertyKey);
			lovTable.setItems(
					FXCollections.observableArrayList(
							lov().getProperties(newVal.propertyName,newVal.propertyKey)));
		});
		
		if(lovComboBox.getItems().size()>0) {
			lovComboBox.setValue(lovComboBox.getItems().get(0));
		}
	}
	
	static class LovProp{
		String propertyName;
		String propertyKey;
		public LovProp(Object[] result) {
			propertyName=(String)result[0];
			propertyKey=(String)result[1];
		}
		@Override
		public String toString() {
			return propertyKey;
		}		
	}
	
	static class DisplayProp{
		Property prop;
		DisplayProp(Property prop){this.prop = prop;}
//		public Stri
	}
}
