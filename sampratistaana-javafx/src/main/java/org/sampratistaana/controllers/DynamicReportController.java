package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilderFactory;

import org.sampratistaana.Mainwindow;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DynamicReportController extends BaseController {
	@FXML private ComboBox<ReportElm> reportLov;
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

			NodeList reportElmList =  rootElm.getElementsByTagName("Report");
			List<ReportElm> reportList = new LinkedList<>();
			for(int i=0;i<reportElmList.getLength();i++) {
				reportList.add(new ReportElm((Element)reportElmList.item(i)));

			}
			setComboxItems(reportLov,reportList);
			reportLov.valueProperty().addListener((obs,oldVal,newVal)->loadReportInputs(newVal));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void loadReportInputs(ReportElm reportElm) {
		paramGrid.getChildren().clear();
		if(reportElm.params==null) {
			try{generateReport();}catch(Exception e) {throw new RuntimeException(e);}
		}else {
			int i=0;
			for(ParamElm paramElm:reportElm.params) {
				Control control=null;
				if(paramElm.dataType.equalsIgnoreCase("date")) {
					control=new DatePicker();
				}else {
					control=new TextField();
				}
				control.setUserData(paramElm);
				paramGrid.add(control, i, i/2);
				i++;
			}
		}
	}

	@FXML
	public void generateReport() throws IOException{

	}

	@FXML
	public void exportToExcel() throws IOException{

	}

	public static class ReportElm{
		String name;
		String query;
		List<ParamElm> params;

		private ReportElm(Element queryElm) {
			name=queryElm.getAttribute("name");
			query=queryElm.getElementsByTagName("Query").item(0).getTextContent();

			NodeList list = queryElm.getElementsByTagName("Param");
			if(list.getLength()>0) {
				StringBuilder buf=new StringBuilder();
				params=new ArrayList<>(list.getLength());
				for(int i=0;i<list.getLength();i++) {
					Element paramElm = ((Element)list.item(0));
					ParamElm param = new ParamElm();
					param.name = paramElm.getAttribute("name");
					param.dataType = paramElm.getAttribute("dataType");
					param.dataType=param.dataType==null?"string":param.dataType.trim();
					params.add(param);
					if(buf.length()>0) {
						buf.append('|');
					}
					buf.append(':').append(param.name);
				}
			}
		}

		@Override
		public String toString() {
			return name;
		}

	}

	static class ParamElm{
		String name;
		String dataType;
	}
}
