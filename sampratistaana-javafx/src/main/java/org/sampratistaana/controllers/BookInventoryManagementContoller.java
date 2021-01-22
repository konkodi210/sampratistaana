package org.sampratistaana.controllers;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Inventory.InventoryType;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class BookInventoryManagementContoller extends BaseController {
	@FXML private Button addBtn;
	@FXML private Button saveBtn;
	@FXML private Button deleteBtn;
	@FXML private TableView<DisplayInventory> bookTable;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(URL location, ResourceBundle resources) {
		bookTable.getColumns().forEach(( TableColumn col)->{
			col.setCellFactory(forTableColumn(DisplayInventory.getConverter(col.getId())));
			col.setCellValueFactory((Callback<CellDataFeatures,ObservableValue>)(p) -> 
			((DisplayInventory) p.getValue()).getProperty(col.getId()));			
		});
		bookTable
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			deleteBtn.setDisable(false);
		});		
		bookTable.setItems(
				FXCollections.observableArrayList(
						stream(new CreditManager().getInventory(InventoryType.BOOK))
						.map(inv -> new DisplayInventory(inv))
						.collect(Collectors.toList())					
						)
				);
	}

	@FXML
	public void addRow() {
		int index=bookTable.getSelectionModel().getSelectedIndex();
		bookTable.getItems().add(
				index<0?0:index,
						new DisplayInventory(new Inventory()));
	}

	@FXML
	public void deleteRow() {
		if(showDeleteConfirmation()) {
			Inventory inv=bookTable.getItems().remove(bookTable.getSelectionModel().getSelectedIndex()).inv;
			if(inv.getInventoryId() !=0) {
				new CreditManager().deleteInventory(inv);
			}
		}
	}

	@FXML
	public void save() {
		//remove empty records which user has not entered any values. Using iterator such that I can remove element while iterating
		for(Iterator<DisplayInventory> itr=bookTable.getItems().iterator();itr.hasNext();) {
			DisplayInventory book = itr.next();
			if(book.name.get()==null 
					||  book.name.get().replaceAll("[\r\n]+","").trim().length()==0  
					|| book.count.get()< 0 || book.unitPrice.get() < 0) {
				itr.remove();
			}
		}

		new CreditManager().saveInventory(
				stream(bookTable.getItems())
				.filter(book -> book.isDirtry())
				.map(book -> book.toInventory())
				.toArray(Inventory[]::new)
				);
	}


	public static class DisplayInventory{
		SimpleStringProperty name=new SimpleStringProperty();
		SimpleDoubleProperty unitPrice= new SimpleDoubleProperty();
		SimpleIntegerProperty count=new SimpleIntegerProperty();
		Inventory inv;

		DisplayInventory(Inventory inv){
			this.inv=inv;
			name.set(inv.getUnitName());
			unitPrice.set(inv.getUnitPrice());
			count.set(inv.getInventoryCount());
		}

		@SuppressWarnings("rawtypes")
		static StringConverter getConverter(String id){
			switch(id) {
			case "name": return new DefaultStringConverter();
			case "unitPrice": return new DoubleStringConverter();
			case "count": return new IntegerStringConverter();
			default: return new DefaultStringConverter();
			}
		}

		@SuppressWarnings("rawtypes")
		Property getProperty(String id){
			switch(id) {
			case "name": return name;
			case "unitPrice": return unitPrice;
			case "count": return count;
			default: throw new RuntimeException("Unsupported id "+id);
			}
		}

		boolean isDirtry() {
			return !name.get().equals(inv.getUnitName())
					|| unitPrice.get()!=inv.getUnitPrice()
					|| count.get()!=inv.getInventoryCount();
		}

		Inventory toInventory() {
//			inv.setLedger(new Ledger()
//					.setEntryType(EntryType.DEBIT)
//					.setEntryCategory(EntryCategory.BOOK_PURCHASE)
//					.setEntryDate(LocalDate.now())
//					//TODO: We need to account for bank purchase
//					.setModeOfTranscation(TransactionMode.CASH)
//					//incase of inventory update, take only changed value
//					.setEntryValue(unitPrice.get()*count.get()-inv.getUnitPrice()*inv.getInventoryCount()));

			return inv
					.setUnitName(name.get())
					.setInventoryType(InventoryType.BOOK)
					.setUnitPrice(unitPrice.get())
					.setInventoryCount(count.get());
		}
	}
}
