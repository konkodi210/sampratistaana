package org.sampratistaana;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		if(key==null) {
			return key;
		}
		ResourceBundle res=getResource();
		String val = res.containsKey(key)?res.getString(key):key;
		if(args!=null && args.length >0) {
			return String.format(val, args);
		}
		return val;
	}

	public static String formatDate(LocalDate date) {
		if(date!=null) {
			//return new SimpleDateFormat(resource.getString("common.date-format")).format(date);
			return DateTimeFormatter.ofPattern(resource.getString("common.date-format")).format(date);
					
		}else {
			return null;
		}
	}
}
