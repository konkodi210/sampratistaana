package org.sampratistaana;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
	private static ResourceBundle resource;

	public static synchronized ResourceBundle getResource() {
		if(resource == null) {
			//TODO: Hardcoding locale for now.
			Locale.setDefault(new Locale("KN"));
			resource=ResourceBundle.getBundle(Messages.class.getPackageName()+".Message",Locale.getDefault());
		}
		return resource;
	}
	
	public static synchronized String getMessage(String key) {
		ResourceBundle res=getResource();
		return res.containsKey(key)?res.getString(key):key;
	}
}
