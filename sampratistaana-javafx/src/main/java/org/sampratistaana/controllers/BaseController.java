package org.sampratistaana.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sampratistaana.Mainwindow;
import org.sampratistaana.Messages;

import javafx.scene.Parent;

public class BaseController {
	private static Map<String, Object> cache=new HashMap<>(); 
	
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
