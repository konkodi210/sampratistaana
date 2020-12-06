package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Expense;
import org.sampratistaana.beans.Ledger.EntryCategory;
import org.sampratistaana.beans.Ledger.EntryType;
import org.sampratistaana.beans.Ledger.TransactionMode;
import org.sampratistaana.beans.Property;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

public class ExpenseEditController extends BaseController{
	public static final String CACHE_KEY="ExpenseEdit";
	@FXML private VBox expenseForm;
	@FXML private Label expenseNo;
	@FXML private DatePicker entryDate;
	@FXML private TextField amount;
	@FXML private ToggleGroup paymentType;
	@FXML private TextField externalTranNo;
	@FXML private TextField description;
	@FXML private ComboBox expenseType;
	@FXML private ComboBox fundType;
	@FXML private ComboBox bankAccount;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		expenseType.getItems().clear();
		expenseType.setItems(FXCollections.observableArrayList(new CreditManager().getExpenseTypes()));
		fundType.getItems().clear();
		fundType.setItems(FXCollections.observableArrayList(new CreditManager().getFundTypes()));
		bankAccount.getItems().clear();
		bankAccount.setItems(FXCollections.observableArrayList(new CreditManager().getBankAccounts()));
		Expense expense = (Expense)getFromCache(CACHE_KEY);
		expenseForm.setUserData(expense);
		expenseNo.setText(String.valueOf(expense.getExpenseId()));
		entryDate.setValue(expense.getLedger().getEntryDate());
		for(Toggle toggle:paymentType.getToggles()) {			
			if(expense.getPaymentType().toString().equals(toggle.getProperties().get("value"))) {
				paymentType.selectToggle(toggle);
				break;
			}
		}
		expenseType.setValue(expense.getExpenseType());
		fundType.setValue(expense.getFundType());
		externalTranNo.setText(expense.getLedger().getExternalTranNo());
		amount.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter()));
		amount.setText(String.valueOf(expense.getLedger().getEntryValue()));		
		description.setText(expense.getLedger().getEntryDesc());

	}

	public void loadExpenses() throws IOException {
		new ExpenseListController().loadExpenses();
	}

	public void saveExpense() throws IOException{
		Expense expense = (Expense)expenseForm.getUserData();
		expense.setExpenseType(expenseType.getValue().toString());
		expense.setFundType(fundType.getValue().toString())
		.getLedger()
			.setEntryCategory(EntryCategory.EXPENSE)
			.setEntryType(EntryType.DEBIT)
			.setEntryValue(Double.parseDouble(amount.getText()))
			.setEntryDate(entryDate.getValue())
			.setExternalTranNo(externalTranNo.getText())
			.setModeOfTranscation(TransactionMode.valueOf((String)paymentType.getSelectedToggle().getProperties().get("value")))
			.setEntryDesc(description.getText());
		
		Long expenseId = new CreditManager().saveExpense(expense);
		loadExpenses();
	}
}
