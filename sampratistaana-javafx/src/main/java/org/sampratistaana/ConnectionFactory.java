package org.sampratistaana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Member;

public class ConnectionFactory {	
	private static final String DB_TEMPLATE_FILE="database.sqlite";
	private static String dbUrl=null;
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(dbUrl());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static synchronized Session dbSession() {
		return getSessionFactory().openSession();
	}
	
	private static synchronized SessionFactory getSessionFactory() {
		try {
			if(sessionFactory==null) {
				registry=new StandardServiceRegistryBuilder()
						.applySetting(Environment.DRIVER, "org.sqlite.JDBC")
						.applySetting(Environment.URL, dbUrl())
						.applySetting(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
						.applySetting(Environment.SHOW_SQL, true)
						.applySetting(Environment.FORMAT_SQL, true)
						.applySetting("hibernate.enable_lazy_load_no_trans", true)
						.build();
				sessionFactory= new MetadataSources(registry)
						.addPackage("org.sampratistaana.beans")
						.addAnnotatedClass(Ledger.class)
						.addAnnotatedClass(Member.class)
						.addAnnotatedClass(Donation.class)
						.getMetadataBuilder()
						.build()
						.getSessionFactoryBuilder()
						.build();
			}
			return sessionFactory;
		}catch(Exception e) {
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
			throw new RuntimeException(e);
		}

	}

	private static String dbUrl() throws IOException{
		if(dbUrl == null) {
			String dbFilePath = System.getProperty("user.home")+File.separator+"db_sampratistana.sqlite";
			Path filePath=new File(dbFilePath).toPath();
			if(!Files.exists(filePath)) {
				Files.copy(ConnectionFactory.class.getResourceAsStream(DB_TEMPLATE_FILE), filePath);
			}
			dbUrl="jdbc:sqlite:"+dbFilePath;
		}
		return dbUrl;
	}
}
