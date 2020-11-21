package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BaseController implements Initializable{
	private static Map<String, Object> cache=new HashMap<>(); 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}

	public Parent loadForm(String formName) throws IOException {
		return Mainwindow.loadForm(formName);
	}
	
	protected synchronized void addToCache(String key,Object value) {
		cache.put(key, value);
	}
	
	protected synchronized Object getFromCache(String key) {
		return cache.get(key);
	}
	
	protected synchronized Object removeFromCache(String key) {
		return cache.remove(key);
	}
	
	protected String formatDate(LocalDate date) {
		return Messages.formatDate(date);
	}
	
	protected <S> void intializeTableColumns(TableView<S> table) {
		table
		.getColumns()
		.forEach(col -> col.setCellValueFactory(new PropertyValueFactory<>(col.getId())));
	}
}
