package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.sampratistaana.ListOfValues;
import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

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

	protected <T> Stream<T> stream(Collection<T> coll){
		return coll == null ? Stream.empty() : coll.stream();
	}

	public <T> StringConverter<T> getStringConvertor(){
		return new StringConverter<T>() {
			private Map<String, T> propertyMap = new HashMap<>();

			@Override
			public String toString(T property) {
				if(property!=null) {
					String value = Messages.getMessage(property.toString());
					propertyMap.put(value, property);
					return value;
				}else {
					return null;
				}
			}

			@Override
			public T fromString(String value) {
				return propertyMap.get(value);
			}
		};
	}
	
	public DoubleStringConverter getCurrnecyConvertor() {
		return new DoubleStringConverter() {

			@Override
			public String toString(Double value) {				
				return String.format("%.2f", value);
			}
			
		};
	}

	protected <T> void setComboxItems(ComboBox<T> box,List<T> items) {
		box.setConverter(getStringConvertor());
		box.setItems(FXCollections.observableArrayList(items));
	}

	protected <T> void setComboBoxValue(ComboBox<T> box,String key) {
		if(key!=null) {
			box.getItems().forEach(obj -> box.getConverter().toString(obj));
			box.setValue(box.getConverter().fromString(Messages.getMessage(key)));
		}
	}

	protected ListOfValues lov() {
		return new ListOfValues();
	}

	protected String getToggleValue(ToggleGroup toggleGroup) {
		if(toggleGroup.getSelectedToggle()!=null) {
			return (String)toggleGroup.getSelectedToggle().getProperties().get("value");
		}
		return null;
	}
}
