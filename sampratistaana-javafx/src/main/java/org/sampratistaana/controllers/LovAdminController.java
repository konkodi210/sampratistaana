package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.Messages;
import org.sampratistaana.beans.Property;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class LovAdminController extends BaseController{
	@FXML private ComboBox<LovProp> lovComboBox;
	@FXML private Button addBtn;
	@FXML private Button saveBtn;
	@FXML private Button deleteBtn;
	@FXML private TableView<DisplayProp> lovTable;

	@Override
	@SuppressWarnings({"unchecked","rawtypes"})
	public void initialize(URL location, ResourceBundle resources) {		
		lovTable.getColumns().forEach((TableColumn col)->{
			if(col.getId().equals("lovVal")) {
				col.setCellFactory(TextFieldTableCell.forTableColumn()); 
				col.setCellValueFactory((Callback<CellDataFeatures,ObservableValue>)(p) -> ((DisplayProp) p.getValue()).lovVal);
			}else {
				throw new NullPointerException("No implementation provided for "+col.getId());
			}
		});
		lovTable
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			deleteBtn.setDisable(false);
		});		
		lovComboBox.setItems(
				FXCollections.observableArrayList(
						stream(lov().getLovs())
						.map(arr -> new LovProp(arr))
						.collect(Collectors.toList())));

		lovComboBox.valueProperty().addListener((obs,oldVal,newVal)-> {
			saveBtn.setDisable(false);
			//deleteBtn.setDisable(false);
			addBtn.setDisable(false);
			System.out.printf("Value CHange %s=%s\n",newVal.propertyName,newVal.propertyKey);
			lovTable.setItems(
					FXCollections.observableArrayList(
							stream(lov().getProperties(newVal.propertyName,newVal.propertyKey))
							.map(prop -> new DisplayProp(prop))
							.collect(Collectors.toList())));
		});

		if(lovComboBox.getItems().size()>0) {
			lovComboBox.setValue(lovComboBox.getItems().get(0));
		}
	}

	@FXML
	public void addRow() throws IOException{
		int index=lovTable.getSelectionModel().getSelectedIndex();
		lovTable.getItems().add(
				index<0?0:index,
						new DisplayProp(new Property()));

	}

	@FXML
	public void deleteRow() throws IOException{
		lovTable.getItems().remove(lovTable.getSelectionModel().getSelectedIndex());
		deleteBtn.setDisable(true);
	}

	@FXML
	public void save() throws IOException{
		System.out.println(stream(lovTable.getItems())
				.filter(x -> x.isDirtry())
				.collect(Collectors.toList()));
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

	public static class DisplayProp{
		Property prop;
		SimpleStringProperty lovVal;
		DisplayProp(Property prop){
			this.prop = prop;
			lovVal=new SimpleStringProperty(Messages.getMessage(prop.getPropertyValue()));
		}	

		boolean isDirtry(){
			return !Messages.getMessage(prop.getPropertyValue()).equals(lovVal.get());
		}
	}
}
