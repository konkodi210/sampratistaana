package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.sampratistaana.ListOfValues;
import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;
import org.sampratistaana.beans.Property;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

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
	
	public StringConverter<Property> getPropertyStringConvertor(){
		return new StringConverter<Property>() {
		    private Map<String, Property> propertyMap = new HashMap<>();

		    @Override
		    public String toString(Property property) {
		    	String value = Messages.getMessage(property.getPropertyValue());
		        propertyMap.put(value, property);
		        return value;
		    }

		    @Override
		    public Property fromString(String value) {
		        return propertyMap.get(value);
		    }
		};
	}
	
	protected void setPropertyComboBoxValue(ComboBox<Property> box,String key) {
		if(key!=null) {
			box.getItems().forEach(obj -> box.getConverter().toString(obj));
			box.setValue(box.getConverter().fromString(Messages.getMessage(key)));
		}
	}
	
	protected ListOfValues lov() {
		return new ListOfValues();
	}
}
