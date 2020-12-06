package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.sampratistaana.CreditManager;
import org.sampratistaana.beans.Expense;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Ledger.TransactionMode;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ExpenseListController extends BaseController{
	private static final String EXPENSE_FORM="NewExpenseForm";

	@FXML private TableView<Expense> expenseList;
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeTableColumns(expenseList);
		//TBD
		expenseList.setItems(FXCollections.observableArrayList(new CreditManager().getAllExpenses()));
		expenseList
		.getSelectionModel()
		.selectedItemProperty()
		.addListener((obs,oldVal,newVal) -> {
			editBtn.setDisable(false);
			deleteBtn.setDisable(false);
		});
	}

	public void loadExpenses() throws IOException {
		loadForm("ExpenseEntryList");	
	}

	public VBox loadExpenseEntryForm() throws IOException {
		
		Expense expense=new Expense()
				.setLedger(
						new Ledger()
						.setEntryDate(LocalDate.now())
						.setModeOfTranscation(TransactionMode.CASH)
						);
		addToCache(ExpenseEditController.CACHE_KEY, expense);
		VBox box= (VBox)loadForm(EXPENSE_FORM);
		return box;
	}

	public void deleteExpense() throws IOException{
		new CreditManager().deleteExpense(expenseList.getSelectionModel().getSelectedItem());
		loadExpenses();
	}

	public void editExpense() throws IOException{
		addToCache(ExpenseEditController.CACHE_KEY, expenseList.getSelectionModel().getSelectedItem());
		loadForm(EXPENSE_FORM);
	}
}
