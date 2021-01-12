package org.sampratistaana.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.BankAccount;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookSaleEditControler extends BaseController {
	protected static final String CACHE_KEY="BookSaleEdit";
	@FXML private Label bookSaleId;
	@FXML private TextField sellerName;
	@FXML private TextField customerName;
	@FXML private DatePicker entryDate;
	@FXML private TextField pan;
	@FXML private ToggleGroup paymentType;
	@FXML private TextField externalTranNo;
	@FXML private TableView<BookEntry> bookSaleTable;
	@FXML private Label grandTotal;
	@FXML private Button saleSaveBtn;
	@FXML private Label depositAccountLabel;
	@FXML private ComboBox<BankAccount> depositAccount;	
	private int index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
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
							if(!(item == null || empty)) {
								spinner.getValueFactory().setValue(item.intValue());
							}
						}
					};					
				});

			}
			col.setCellValueFactory((Callback<CellDataFeatures,ObservableValue>)(p) -> {
				Property prop=((BookEntry) p.getValue()).getProperty(col.getId());
				if(col.getId().equals("totalPrice")) {					
					prop.addListener((observable, oldVal, newValue) -> {
						Double sum=stream(bookSaleTable.getItems())
								.map(x -> x.totalPrice.get())
								.collect(Collectors.summingDouble(Double::doubleValue));
						grandTotal.setText(String.valueOf(sum));
					});
				}
				return prop;
			});
		});
		
		paymentType.selectedToggleProperty().addListener((obs,old,toggle) -> {
			depositAccount.setVisible(!toggle.getProperties().get("value").equals("CASH"));
			depositAccountLabel.setVisible(depositAccount.isVisible());
		});

		index=1;
		List<BookSale> editList=(List<BookSale>)removeFromCache(CACHE_KEY);
		Map<Long,BookSale> bookSaleMap=stream(editList)
				.collect(Collectors.toMap(book -> book.getInventory().getInventoryId(), book -> book));

		bookSaleTable.setItems(		
				FXCollections.observableList(
						stream(new CreditManager().getInventory(InventoryType.BOOK))
						.map(inv -> new BookEntry(inv, bookSaleMap.get(inv.getInventoryId()),index++))
						.collect(Collectors.toList())));

		//save ledger reference for making a sale. As per design, we will have one ledger entry for complete sale
		Ledger ledger = bookSaleMap.size()>0 ? editList.get(0).getLedger():new Ledger();
		bookSaleTable.setUserData(ledger);
		for(Toggle toggle:paymentType.getToggles()) {			
			if(ledger.getModeOfTranscation()!=null && ledger.getModeOfTranscation().toString().equals(toggle.getProperties().get("value"))) {
				paymentType.selectToggle(toggle);
				break;
			}
		}
		pan.setText(ledger.getPanNo());
		externalTranNo.setText(ledger.getExternalTranNo());
		entryDate.setValue(ledger.getEntryDate()!=null? ledger.getEntryDate(): LocalDate.now());

		setComboxItems(depositAccount,lov().getBankAccountTable());

		if(ledger.getBankAccount()!=null) {
			depositAccount.setValue(ledger.getBankAccount());
		}else if(depositAccount.getItems().size()>0) {
			depositAccount.setValue(depositAccount.getItems().get(0));
		}
		
		//enable save only if any book is selected for sale
		grandTotal.textProperty().addListener((ob, old, newVal) -> saleSaveBtn.setDisable(Double.parseDouble(newVal) == 0));
		grandTotal.setText(String.valueOf(ledger.getEntryValue()));

		if(bookSaleMap.size() > 0) {
			for(BookSale bookSale:bookSaleMap.values()) {
				customerName.setText(bookSale.getCustomerName());
				sellerName.setText(bookSale.getSellerName());
				break;
			}
		}
	}

	public void makeSale() throws Exception {
		TransactionMode tranMode = TransactionMode.valueOf(getToggleValue(paymentType));
		Ledger ledger= ((Ledger)bookSaleTable.getUserData())
				.setModeOfTranscation(tranMode)
				.setExternalTranNo(externalTranNo.getText())
				.setBankAccount(tranMode == TransactionMode.CASH?null:depositAccount.getValue())
				.setEntryDate(entryDate.getValue())
				.setPanNo(pan.getText());

		//Collect the entry from table table and make array of books sale to save
		new CreditManager().makeBookSale(stream(bookSaleTable.getItems())
				//select only books where quantity is more than zero or current count is zero but old count is greater than zero
				.filter(x -> x.quantity.get() > 0 || x.quantity.get() ==0 && x.bookSale.getUnitCount() >0 )
				.map(x -> x.bookSale
						.setCustomerName(customerName.getText())
						.setSellerName(sellerName.getText())
						.setUnitCount(x.quantity.get())
						.setInventory(x.inventory)
						.setLedger(ledger))
				.toArray(BookSale[]::new));

		loadForm("BookSaleList");
	}

	public void cancel() throws Exception {
		loadForm("BookSaleList");
	}

	/*
	 * Book sale table is an editable table. This means we need to capture the user entry from the table and
	 * save in the database. Hence, we need UI bean class which reads the values from the DTO bean and UI compatible property.
	 * 
	 * To make code short, we could created the DTO bean class which works for both UI as well as Entity layer. But it against the principle
	 */
	public static class BookEntry{
		private SimpleIntegerProperty serialNo=new SimpleIntegerProperty();
		private SimpleStringProperty unitName=new SimpleStringProperty();
		private SimpleDoubleProperty unitPrice=new SimpleDoubleProperty();
		private SimpleIntegerProperty quantity=new SimpleIntegerProperty();
		private SimpleDoubleProperty totalPrice=new SimpleDoubleProperty();
		private Inventory inventory;
		private BookSale bookSale;

		BookEntry(Inventory inventory,BookSale bookSale,int index){
			serialNo.set(index);
			unitName.set(inventory.getUnitName());
			unitPrice.set(inventory.getUnitPrice());
			quantity.addListener((ob, old, newVal) -> totalPrice.set(unitPrice.get()*newVal.intValue()));
			this.inventory=inventory;
			if(bookSale!=null) {
				quantity.set(bookSale.getUnitCount());
				this.bookSale = bookSale;
			}else {
				this.bookSale = new BookSale();
			}
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

		public void setUnitCount(int unitCount) {
			this.quantity.set(unitCount);
		}

	}
}
