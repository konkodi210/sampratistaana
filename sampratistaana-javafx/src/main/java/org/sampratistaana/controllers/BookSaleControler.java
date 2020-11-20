package org.sampratistaana.controllers;

import java.net.URL;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookSaleControler extends BaseController {
	@FXML private Label bookSaleId;
	@FXML private Label entryDate;
	@FXML private TableView<BookEntry> booksaleTable;
	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		booksaleTable.getColumns().forEach((TableColumn col) -> {
			col.setCellValueFactory((Callback<CellDataFeatures,ObservableValue>)(p) 
					-> ((BookEntry) p.getValue()).getProperty(col.getId()));

			if(col.getId().equals("quantity")) {
				col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
				col.setOnEditCommit((EventHandler<CellEditEvent<BookEntry, Integer>>)(t) 
						-> ((BookEntry) t.getTableView()
								.getItems()
								.get(t.getTablePosition().getRow())
								).quantity.set(t.getNewValue())
						);
			}
		});

		index=1;
		booksaleTable.setItems(		
				FXCollections.observableList(
						new CreditManager()
						.getInventory(InventoryType.BOOK)
						.stream()
						.map(inv -> new BookEntry(inv, index++))
						.collect(Collectors.toList())));

	}	

	public void makeSale() {

	}

	public void cancel() {

	}

	public static class BookEntry{
		private SimpleIntegerProperty serialNo=new SimpleIntegerProperty();
		private SimpleStringProperty unitName=new SimpleStringProperty();
		private SimpleDoubleProperty unitPrice=new SimpleDoubleProperty();
		private SimpleIntegerProperty quantity=new SimpleIntegerProperty();
		private SimpleDoubleProperty totalPrice=new SimpleDoubleProperty();

		BookEntry(Inventory inventory,int index){
			serialNo.set(index);
			unitName.set(inventory.getUnitName());
			unitPrice.set(inventory.getUnitPrice());
			quantity.addListener((observable, oldVal, newValue) -> {
				System.out.println("Value "+newValue);
				totalPrice.set(unitPrice.get()*newValue.intValue());
			});
		}

		public Property getProperty(String id){
			switch(id) {
			case "serialNo":return serialNo;
			case "unitName":return unitName;
			case "unitPrice":return unitPrice;
			case "quantity":return quantity;
			case "totalPrice":return totalPrice;
			}
			throw new NullPointerException("No Property for id="+id);
		}
	}
}
