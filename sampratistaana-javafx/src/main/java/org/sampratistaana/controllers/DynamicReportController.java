package org.sampratistaana.controllers;

import static org.sampratistaana.ConnectionFactory.getConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;
import org.sampratistaana.ReportManager;
import org.sampratistaana.beans.DynamicReport;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DynamicReportController extends BaseController {
	@FXML private ComboBox<DynamicReport> reportLov;
	@FXML private TableView<Map<String,String>> reportTable;
	@FXML private GridPane paramGrid;
	@FXML private Button excelExportBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {			
			setComboxItems(reportLov, new ReportManager().getConfiguredReports());
			reportLov.valueProperty().addListener((obs,oldVal,newVal)->{ 
				excelExportBtn.setDisable(false);
				generateReport(newVal);				
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void generateReport(DynamicReport report) {
		System.out.printf("Executing %s and Query=%s\n",report.getReportName(),report.getReportQuery());

		try(Connection con=getConnection();
				PreparedStatement ps=con.prepareStatement(report.getReportQuery());
				ResultSet rs=ps.executeQuery();){
			//reportTable.getItems().clear();
			reportTable.getColumns().clear();
			ResultSetMetaData rsm=rs.getMetaData();
			List<String> colList=new ArrayList<String>(rsm.getColumnCount());
			TableColumn[] tabColArr=new TableColumn[rsm.getColumnCount()];
			for(int i=1;i<=rsm.getColumnCount();i++) {
				colList.add(rsm.getColumnName(i));
				TableColumn col=new TableColumn<>();
				col.setText(Messages.getMessage(rsm.getColumnName(i)));
				col.setCellValueFactory(new MapValueFactory<>(rsm.getColumnName(i)));
				tabColArr[i-1]=col;				
			}
			reportTable.getColumns().addAll(tabColArr);
			Map<String,String> row=new HashMap<String, String>();
			List<Map<String,String>> rowList=new LinkedList<>();
			while(rs.next()) {
				for(String col:colList) {
					String val=null;
					try {
						if(col.contains("_DATE") || col.contains("DATE_") || col.contains("_DATE_")) {
							val=Messages.formatDate(Instant.ofEpochMilli(rs.getLong(col)).atZone(ZoneId.systemDefault()).toLocalDate());
						}else {
							val=rs.getString(col);
						}
					}catch(Exception e) {
						val = rs.getString(col);
					}
					row.put(col, val);				
				}
				rowList.add(new HashMap<>(row));
			}
			
			setTableItems(reportTable,rowList);
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@FXML
	public void exportToExcel() throws Exception{
		FileChooser fc = new FileChooser();
		fc.setTitle("Excel Export");
		fc.setInitialFileName("export.xlsx");
		fc.getExtensionFilters().add(new ExtensionFilter("Excel File", "*.xlsx"));
		File exportFile = fc.showSaveDialog(Mainwindow.getScene().getWindow());
		try(Workbook book = new XSSFWorkbook()){
			DynamicReport report = reportLov.getSelectionModel().getSelectedItem();
			Sheet sh= book.createSheet(report.getReportName());

			int rownum = 0;
			int cellNum=0;
			Row row = sh.createRow(rownum++);
			for(TableColumn col:reportTable.getColumns()) {
				row.createCell(cellNum++).setCellValue(col.getText());
			}
			for(Map rowVal:reportTable.getItems()) {
				cellNum=0;
				row = sh.createRow(rownum++);
				for(TableColumn col:reportTable.getColumns()) {
					row.createCell(cellNum++).setCellValue((String)rowVal.get(col.getText()));
				}
			}

			try(OutputStream out = new FileOutputStream(exportFile)){
				book.write(out);
			}
		}
	}

}


