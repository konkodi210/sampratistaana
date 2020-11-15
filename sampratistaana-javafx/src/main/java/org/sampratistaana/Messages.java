package org.sampratistaana;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
	private static ResourceBundle resource;

	public static synchronized ResourceBundle getResource() {
		if(resource == null) {
			//TODO: Hardcoding locale for now.
			Locale.setDefault(new Locale("KN"));
			resource=ResourceBundle.getBundle(Messages.class.getPackage().getName()+".Message",Locale.getDefault());
		}
		return resource;
	}
	
	public static synchronized String getMessage(String key,Object...args) {
		ResourceBundle res=getResource();
		String val = res.containsKey(key)?res.getString(key):key;
		if(args!=null && args.length >0) {
			return String.format(val, args);
		}
		return val;
	}
	
	public static String formatDate(long date) {
		if(date>0) {
		return new SimpleDateFormat(resource.getString("common.date-format")).format(new Date(date));
		}else {
			return null;
		}
	}
}
