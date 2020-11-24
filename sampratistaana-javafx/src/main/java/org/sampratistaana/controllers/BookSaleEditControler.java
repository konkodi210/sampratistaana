package org.sampratistaana.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.BookSale;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Inventory.InventoryType;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookSaleEditControler extends BaseController {
	@FXML private Label bookSaleId;
	@FXML private TextField sellerName;
	@FXML private TextField customerName;
	@FXML private DatePicker entryDate;
	@FXML private TableView<BookEntry> bookSaleTable;
	@FXML private Label grandTotal;
	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entryDate.setValue(LocalDate.now());
		bookSaleTable.getColumns().forEach((TableColumn col) -> {
			if(col.getId().equals("quantity")) {
				//Add Spinner for quantity
				col.setCellFactory((Callback<TableColumn<BookEntry,Integer>,TableCell<BookEntry,Integer>>)(p)->{					
					return new TableCell<BookEntry,Integer>() {
						Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, 0, 1);
						{
							spinner.valueProperty().addListener((o, oldValue, newValue) -> 
							((WritableValue<Number>) getTableColumn().getCellObservableValue(getTableRow().getItem())).setValue(newValue));
						}
						@Override
						protected void updateItem(Integer item, boolean empty) {
							setGraphic(spinner);
							super.updateItem(item, empty);							
						}
					};					
				});

			}
			col.setCellValueFactory((Callback<CellDataFeatures,ObservableValue>)(p) -> {
				Property prop=((BookEntry) p.getValue()).getProperty(col.getId());
				if(col.getId().equals("totalPrice")) {					
					prop.addListener((observable, oldVal, newValue) -> {
						Double sum=bookSaleTable
								.getItems()
								.stream()
								.map(x -> x.totalPrice.get())
								.collect(Collectors.summingDouble(Double::doubleValue));
						grandTotal.setText(String.valueOf(sum));
					});
				}
				return prop;
			});
		});

		index=1;
		bookSaleTable.setItems(		
				FXCollections.observableList(
						new CreditManager()
						.getInventory(InventoryType.BOOK)
						.stream()
						.map(inv -> new BookEntry(inv, index++))
						.collect(Collectors.toList())));

	}

	public void makeSale() throws Exception {
		Ledger ledger= new Ledger()
				.setModeOfTranscation(TransactionMode.CASH); //TODO: Need collect the transaction mode,PAN and trasaction id 
		//Collect the entry from table table and make array of books sale to save
		new CreditManager().makeBookSale(
				bookSaleTable
				.getItems()
				.stream()
				.filter(x -> x.quantity.get() > 0)//select only books where quantity is more than zero
				.map(x -> new BookSale()
						.setCustomerName(customerName.getText())
						.setUnitCount(x.quantity.get())
						.setInventory(x.inventory)
						.setLedger(ledger))
				.toArray(BookSale[]::new));

		loadForm("BookSaleList");
	}

	public void cancel() throws Exception {
		loadForm("BookSaleList");
	}

	public static class BookEntry{
		private SimpleIntegerProperty serialNo=new SimpleIntegerProperty();
		private SimpleStringProperty unitName=new SimpleStringProperty();
		private SimpleDoubleProperty unitPrice=new SimpleDoubleProperty();
		private SimpleIntegerProperty quantity=new SimpleIntegerProperty();
		private SimpleDoubleProperty totalPrice=new SimpleDoubleProperty();
		private Inventory inventory;

		BookEntry(Inventory inventory,int index){
			serialNo.set(index);
			unitName.set(inventory.getUnitName());
			unitPrice.set(inventory.getUnitPrice());
			quantity.addListener((ob, old, newVal) -> totalPrice.set(unitPrice.get()*newVal.intValue()));
			this.inventory=inventory;
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
