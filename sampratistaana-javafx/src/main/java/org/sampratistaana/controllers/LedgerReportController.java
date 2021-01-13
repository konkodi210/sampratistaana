package org.sampratistaana.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;
import org.sampratistaana.ReportManager;
import org.sampratistaana.beans.Ledger;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
		setTableItems(
				reportTable,
				stream(new ReportManager()
						.generateFinacialAuditReport(fromDate.getValue(), toDate.getValue()))
				.map(ledger -> new Report(ledger))
				.collect(Collectors.toList())
				);
//		reportTable.setItems(
//				FXCollections.observableList(
//						stream(new ReportManager()
//								.generateFinacialAuditReport(fromDate.getValue(), toDate.getValue()))
//						.map(ledger -> new Report(ledger))
//						.collect(Collectors.toList())
//						)
//				);
	}

	@FXML
	private void exportToExcel() throws IOException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Excel Export");
		fc.setInitialFileName("export.xlsx");
		fc.getExtensionFilters().add(new ExtensionFilter("Excel File", "*.xlsx"));
		File exportFile = fc.showSaveDialog(Mainwindow.getScene().getWindow());
		Workbook wb = WorkbookFactory.create(Mainwindow.class.getResourceAsStream("DayBook.xlsx"));
		Sheet sh=wb.getSheet("Day Book");

		sh.getRow(4)
		.getCell(0)
		.setCellValue(String.format("%s to %s", formatDate(fromDate.getValue()),formatDate(toDate.getValue())));

		int rownum = 7;
		int cellNum=0;		
		for(Report report:reportTable.getItems()) {
			Row row = sh.createRow(rownum++);
			cellNum=0;
			row.createCell(cellNum++).setCellValue(report.date);
			row.createCell(cellNum++).setCellValue(report.particular);
			row.createCell(cellNum++).setCellValue(report.voucherType);
			row.createCell(cellNum++).setCellValue(report.voucherNo);
			row.createCell(cellNum++).setCellValue(report.debit);
			row.createCell(cellNum++).setCellValue(report.credit);
		}
		try(OutputStream out = new FileOutputStream(exportFile)){
			wb.write(out);
		}
	}

	public static class Report{
//		static final NumberFormat fmt= NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		String date;
		String particular;
		String voucherType;
		long voucherNo;
		String debit;
		String credit;

		Report(Ledger ledger){
			date=Messages.formatDate(ledger.getEntryDate(),"dd-MM-yyyy");
			switch(ledger.getEntryCategory()) {
			case EXPENSE:particular=ledger.getEntryDesc();break;
			case MEMBER: particular="New Member Enrollment";break;
			case DONATION: particular="Donation";break;
			case BOOK_SALE: particular="Book Sale";break;
			default:throw new RuntimeException("Not implemented for this type "+ledger.getEntryCategory());
			}
			switch(ledger.getEntryType()) {
			case CREDIT:
				voucherType="Receipt";
				credit=Messages.formatCurrency(ledger.getEntryValue());
				break;
			case DEBIT:
				voucherType="Payment";
				debit=Messages.formatCurrency(ledger.getEntryValue());
				break;
			default:throw new RuntimeException("Not implemented for Entry type "+ledger.getEntryType());
			}
			voucherNo=ledger.getEntryNo();			
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

		public long getVoucherNo() {
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
