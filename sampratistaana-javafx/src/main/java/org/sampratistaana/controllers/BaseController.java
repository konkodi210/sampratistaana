package org.sampratistaana.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

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
	
	protected String formatDate(long date) {
		return Messages.formatDate(date);
	}
}
