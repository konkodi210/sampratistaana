package org.sampratistaana;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	private static final String DB_TEMPLATE_FILE="database.sqlite";
	private String dbUrl=null;
	public synchronized Connection getConnection() {
		try {
			if(dbUrl == null) {
				String dbFilePath = System.getProperty("user.home")+"db_sampratistana.sqlite";
				Path filePath=new File(dbFilePath).toPath();
				if(!Files.exists(filePath)) {
					Files.copy(ConnectionFactory.class.getResourceAsStream(DB_TEMPLATE_FILE), filePath);
				}
				dbUrl="jdbc:sqlite:"+dbFilePath;
			}
			return DriverManager.getConnection(dbUrl);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
