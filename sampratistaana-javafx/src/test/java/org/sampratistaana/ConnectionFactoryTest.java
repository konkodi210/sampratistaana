package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class ConnectionFactoryTest {

	@Test
	public void testInit() throws Exception{
		Path existingFile = null;
		Path dbFile = Paths.get(System.getProperty("user.home"),"db_sampratistana.sqlite");

		//if the db file already exists, move to temp directory
		if(Files.exists(dbFile)) {
			Path tmpDir=Files.createTempDirectory("tmp");
			existingFile = Paths.get(tmpDir.toString(),"tmp.sqlite");
			Files.move(dbFile, existingFile);
		}
		try(Connection con=ConnectionFactory.getConnection()){
			assertThat("New DB file must be created in user home directory",Files.exists(dbFile),equalTo(true));
		}finally {
			//cleanup once work is done
			if(existingFile!=null) {
				Files.move(existingFile, dbFile,StandardCopyOption.REPLACE_EXISTING);
			}else {
				Files.delete(dbFile);
			}
		}
	}

	@Test
	public void testConnection() throws Exception {
		try(Connection con=ConnectionFactory.getConnection()){
			assertThat("Connection object should come not null", con,notNullValue());
		}
	}
	
	@Test
	public void testDBQuery() throws Exception{
		try(Connection con=ConnectionFactory.getConnection();
				PreparedStatement ps=con.prepareStatement("select 1");
				ResultSet rs=ps.executeQuery();){
			assertThat("Must have one record", rs.next(),equalTo(true));
			assertThat("First column must be 1", rs.getString(1),equalTo("1"));
		}
	}
}
