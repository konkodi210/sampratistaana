package org.sampratistaana.controllers;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.sampratistaana.Messages;
import org.sampratistaana.ReportManager;
import org.sampratistaana.beans.Ledger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class LedgerReportController extends BaseController{
	@FXML private DatePicker fromDate; 
	@FXML private DatePicker toDate; 
	@FXML private TableView<Report> reportTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fromDate.setValue(LocalDate.now().minusMonths(1));
		toDate.setValue(LocalDate.now());
		intializeTableColumns(reportTable);
		generateReport();
	}
	
	@FXML
	private void generateReport() {
		reportTable.setItems(
				FXCollections.observableList(
						stream(new ReportManager()
								.generateFinacialAuditReport(fromDate.getValue(), toDate.getValue()))
						.map(ledger -> new Report(ledger))
						.collect(Collectors.toList())
						)
				);
	}
	
	public static class Report{
		static final NumberFormat fmt= NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		String date;
		String particular;
		String voucherType;
		String voucherNo;
		String debit;
		String credit;

		Report(Ledger ledger){
			date=Messages.formatDate(ledger.getEntryDate(),"dd-MM-yyyy");
			switch(ledger.getEntryCategory()) {
			case EXPENSE:particular=ledger.getEntryDesc();break;
			case MEMBER: particular="New Member Enrollment";break;
			case DONATION: particular="Donnation";break;
			case BOOK_SALE: particular="Book Sale";break;
			default:throw new RuntimeException("Not implemented for this type "+ledger.getEntryCategory());
			}
			switch(ledger.getEntryType()) {
			case CREDIT:
				voucherType="Receipt";
				credit=fmt.format(ledger.getEntryValue());
				break;
			case DEBIT:
				voucherType="Payment";
				debit=fmt.format(ledger.getEntryValue());
				break;
			default:throw new RuntimeException("Not implemented for Entry type "+ledger.getEntryType());
			}
			voucherNo=String.valueOf(ledger.getEntryNo());			
		}
		
		public String getDate() {
			return date;
		}

		public String getParticular() {
			return particular;
		}

		public String getVoucherType() {
			return voucherType;
		}

		public String getVoucherNo() {
			return voucherNo;
		}

		public String getDebit() {
			return debit;
		}

		public String getCredit() {
			return credit;
		}
		
	}
}
