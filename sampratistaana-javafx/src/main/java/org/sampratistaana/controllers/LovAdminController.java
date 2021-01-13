package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
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
			setTableItems(lovTable,
					stream(lov().getProperties(newVal.propertyName,newVal.propertyKey))
					.map(prop -> new DisplayProp(prop))
					.collect(Collectors.toList()));
//			lovTable.setItems(
//					FXCollections.observableArrayList(
//							stream(lov().getProperties(newVal.propertyName,newVal.propertyKey))
//							.map(prop -> new DisplayProp(prop))
//							.collect(Collectors.toList())));
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
		Property prop=lovTable.getItems().remove(lovTable.getSelectionModel().getSelectedIndex()).prop;
		if(prop.getPropertyKey()!=null) {
			lov().saveLov(null, Arrays.asList(prop));
		}
		
		deleteBtn.setDisable(true);
	}

	@FXML
	public void save() throws IOException{
		//remove empty records which user has not entered any values. Using iterator such that I can remove element while iterating
		for(Iterator<DisplayProp> itr=lovTable.getItems().iterator();itr.hasNext();) {
			DisplayProp prop = itr.next();
			if(prop.lovVal.get()==null || prop.lovVal.get().trim().length()==0 ) {
				itr.remove();
			}
		}
	
		final LovProp lovProp = lovComboBox.getSelectionModel().getSelectedItem();
		lov().saveLov((stream(lovTable.getItems())
				.filter(x -> x.isDirtry())
				.map(x -> x.prepareForSave(lovProp.propertyName, lovProp.propertyKey))
				.collect(Collectors.toList())),null);
	}

	public static class LovProp{
		String propertyName;
		String propertyKey;
		LovProp(Object[] result) {
			propertyName=(String)result[0];
			propertyKey=(String)result[1];
		}
		@Override
		public String toString() {
			return Messages.getMessage("admin.dropdown."+propertyKey);
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
			return lovVal.get()!=null && lovVal.get().trim().length()>0 && (prop.getPropertyKey()==null 
					|| !Messages.getMessage(prop.getPropertyValue()).equals(lovVal.get().trim()));
		}

		Property prepareForSave(String propName, String propKey) {
			prop.setFlag("Y");
			prop.setPropertyName(propName);
			prop.setPropertyKey(propKey);
			prop.setPropertyValue(lovVal.get());
			return prop;
		}
	}
}
