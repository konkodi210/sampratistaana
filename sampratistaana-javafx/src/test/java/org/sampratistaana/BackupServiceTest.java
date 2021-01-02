package org.sampratistaana;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.walk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sampratistaana.ConnectionFactory.dbSession;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.Property;

public class BackupServiceTest {
	private static ThreadLocal<TestData> propRec=new ThreadLocal<>();

	class TestData{
		Property prop=new Property()
				.setFlag("Y")
				.setPropertyName(UUID.randomUUID().toString())
				.setPropertyKey(UUID.randomUUID().toString())
				.setPropertyValue(UUID.randomUUID().toString());
		String syncDir;
		Path getPath() {
			return Path.of(syncDir,"samprathistaana","db","PROPERTIES","PROPERTIES_"+prop.getPropertyId()+".json");
		}
	}

	@Before
	public void setup() throws Exception {
		TestData data=new TestData();
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(data.prop);
			tran.commit();
		}		
		String dir=createTempDirectory("onedirve").toString();
		System.setProperty("ONEDRIVE_DIR", dir);

		data.syncDir=dir;
		propRec.set(data);
		new BackupService().performBackup();
	}


	@After
	public void tearDown() throws Exception{
		if(propRec.get().prop!=null) {
			try(Session session=dbSession()){
				Transaction tran=session.beginTransaction();
				session.delete(propRec.get().prop);
				tran.commit();
			}
		}
		walk(Path.of(System.getProperty("ONEDRIVE_DIR")))
		.sorted(Comparator.reverseOrder())
		.map(Path::toFile)
		.forEach(File::delete);
	}

	@Test
	public void testNewRecord() throws Exception {		
		assertThat("new File must be created",Files.exists(propRec.get().getPath()));
	}

	@Test
	public void testUpdateRecord() throws Exception{
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			propRec.get().prop.setPropertyValue("updated");
			session.saveOrUpdate(propRec.get().prop);
			tran.commit();
		}
		new BackupService().performBackup();
		assertThat("File must be updated", Files.readString(propRec.get().getPath()).contains("\"updated\""));
	}

	@Test
	public void testDeleteRecord() throws Exception {
		Path path=propRec.get().getPath();
		try(Session session=dbSession()){
			Transaction tran=session.beginTransaction();
			session.delete(propRec.get().prop);
			tran.commit();	
			propRec.get().prop=null;
		}
		new BackupService().performBackup();
		assertThat("File should be deleted",!Files.exists(path));
		
	}
}
