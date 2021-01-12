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
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.BookSale;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Expense;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Ledger;
import org.sampratistaana.beans.Member;
import org.sampratistaana.beans.Property;

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
						.applySetting(Environment.STATEMENT_BATCH_SIZE, 10)
						.applySetting("hibernate.enable_lazy_load_no_trans", true)
						.build();
				sessionFactory= new MetadataSources(registry)
						.addPackage("org.sampratistaana.beans")
						.addAnnotatedClass(Ledger.class)
						.addAnnotatedClass(Member.class)
						.addAnnotatedClass(Donation.class)
						.addAnnotatedClass(Inventory.class)
						.addAnnotatedClass(BookSale.class)
						.addAnnotatedClass(Expense.class)
						.addAnnotatedClass(Property.class)
						.addAnnotatedClass(BankAccount.class)
						.getMetadataBuilder()
						.applySqlFunction("GROUP_CONCAT", new SQLFunctionTemplate(StringType.INSTANCE, "GROUP_CONCAT(?1)"))
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

	private static synchronized String dbUrl() throws IOException{
		if(dbUrl == null) {
			String dbFilePath = System.getProperty("user.home")+File.separator+"db_sampratistana_20210112.sqlite";
			Path filePath=new File(dbFilePath).toPath();
			if(!Files.exists(filePath)) {
				Files.copy(ConnectionFactory.class.getResourceAsStream(DB_TEMPLATE_FILE), filePath);
			}
			dbUrl="jdbc:sqlite:"+dbFilePath;
		}
		return dbUrl;
	}
	
	public static BackupRepository getBackupService() throws IOException{
		BackupRepository service=new OneDriveBackupRepository();
		service.init();
		return service;
	}
}
