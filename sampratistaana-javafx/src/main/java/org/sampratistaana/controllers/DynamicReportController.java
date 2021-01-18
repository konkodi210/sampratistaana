package org.sampratistaana.controllers;

import static org.sampratistaana.ConnectionFactory.getConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections4.map.HashedMap;
import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;
import org.sampratistaana.ReportManager;
import org.sampratistaana.beans.DynamicReport;
import org.w3c.dom.Element;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;

public class DynamicReportController extends BaseController {
	@FXML private ComboBox<DynamicReport> reportLov;
	@FXML private TableView<Map<String,String>> reportTable;
	@FXML private GridPane paramGrid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Element rootElm = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(Mainwindow.class.getResourceAsStream("report-config.xml"))
					.getDocumentElement();
			setComboxItems(reportLov, new ReportManager().getConfiguredReports());
			reportLov.valueProperty().addListener((obs,oldVal,newVal)-> generateReport(newVal));
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
			reportTable.getItems().clear();
			reportTable.getColumns().clear();
			ResultSetMetaData rsm=rs.getMetaData();
			List<String> colList=new ArrayList<String>(rsm.getColumnCount());
			for(int i=1;i<=rsm.getColumnCount();i++) {
				colList.add(rsm.getColumnName(i));
				TableColumn col=new TableColumn<>();
				col.setText(rsm.getColumnName(i));
				col.setCellValueFactory(new MapValueFactory<>(rsm.getColumnName(i)));
				reportTable.getColumns().add(col);
			}
			Map<String,String> row=new HashedMap<String, String>();
			while(rs.next()) {
				for(String col:colList) {
					String val=null;
					try {
						if(col.contains("_DATE")) {
							val=Messages.formatDate(Instant.ofEpochMilli(1609736400000l).atZone(ZoneId.systemDefault()).toLocalDate());
						}else {
							val=rs.getString(col);
						}
					}catch(Exception e) {
						val = rs.getString(col);
					}
					row.put(col, val);				
				}
				reportTable.getItems().add(new HashedMap<>(row));
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	public void exportToExcel() throws IOException{

	}

}


