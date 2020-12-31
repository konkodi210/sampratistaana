package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.getConnection;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BackupService {
	public static void main(String[] args) throws Exception{
		new BackupService().performBackup();
	}

	public void performBackup() throws Exception{
		String tempDir=Files.createTempDirectory("backup").toString();
		System.out.println("BackDir:"+tempDir);
		Set<String> srcFileSet=new HashSet<>();
		try(Connection con=getConnection();
				Statement st=con.createStatement();){
			backup("LEDGER","ENTRY_NO",tempDir,st,srcFileSet);
			backup("MEMBER","MEMBER_NO",tempDir,st,srcFileSet);
			backup("BANK_ACCOUNTS","BANK_ACCOUNT_ID",tempDir,st,srcFileSet);
			backup("BOOK_SALE","BOOK_SALE_ID",tempDir,st,srcFileSet);
			backup("DONATION","DONATION_ID",tempDir,st,srcFileSet);
			backup("EXPENSE","EXPENSE_ID",tempDir,st,srcFileSet);
			backup("Inventory","INVENTORY_ID",tempDir,st,srcFileSet);
			backup("PROPERTIES","PROPERTY_ID",tempDir,st,srcFileSet);
			
		}
	}

	private void backup(String table,String idCol,String tmpDir,Statement st,Set<String> srcFileSet) throws Exception {
		String dir=Files.createDirectories(Paths.get(tmpDir, table)).toString();
		try(ResultSet rs=st.executeQuery("SELECT * FROM "+table)){
			ResultSetMetaData rsm=rs.getMetaData();
			List<String> colList=new ArrayList<String>(rsm.getColumnCount());
			for(int i=1;i<=rsm.getColumnCount();i++) {
				colList.add(rsm.getColumnName(i));
			}

			//let us maintain consistent order
			Collections.sort(colList);
			Map<String,String> row=new LinkedHashMap<>();
			Gson gson=new GsonBuilder().setPrettyPrinting().create();
			while(rs.next()) {
				for(String col:colList) {
					row.put(col, rs.getString(col));
				}
				String path=String.format("%s_%s.json", table,row.get(idCol));
				Path tmpFilePath=Paths.get(dir,path);
				srcFileSet.add(Paths.get(tmpDir).relativize(tmpFilePath).toString());
				Files.write(tmpFilePath, gson.toJson(row).getBytes());
			}
		}
	}
	
}
