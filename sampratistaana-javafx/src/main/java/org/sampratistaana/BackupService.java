package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.getConnection;

import java.io.IOException;
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
		Path tempDir=Files.createTempDirectory("backup");		
		String dbRootDir=Path.of(tempDir.toString(),"samprathistaana","db").toString();
		System.out.println("BackDir:"+dbRootDir);
		Set<String> srcFileSet=new HashSet<>();
		try(Connection con=getConnection();
				Statement st=con.createStatement();){
			backupDb("LEDGER","ENTRY_NO",tempDir,dbRootDir,st,srcFileSet);
			backupDb("MEMBER","MEMBER_NO",tempDir,dbRootDir,st,srcFileSet);
			backupDb("BANK_ACCOUNTS","BANK_ACCOUNT_ID",tempDir,dbRootDir,st,srcFileSet);
			backupDb("BOOK_SALE","BOOK_SALE_ID",tempDir,dbRootDir,st,srcFileSet);
			backupDb("DONATION","DONATION_ID",tempDir,dbRootDir,st,srcFileSet);
			backupDb("EXPENSE","EXPENSE_ID",tempDir,dbRootDir,st,srcFileSet);
			backupDb("INVENTORY","INVENTORY_ID",tempDir,dbRootDir,st,srcFileSet);
			backupDb("PROPERTIES","PROPERTY_ID",tempDir,dbRootDir,st,srcFileSet);
			
		}
		syncFile(new OneDriveBackupRepository(),srcFileSet,tempDir);
	}

	private void backupDb(String table
			,String idCol
			,Path tempDir
			,String dbRootDir
			,Statement st
			,Set<String> srcFileSet) throws Exception {
		String dir=Files.createDirectories(Path.of(dbRootDir, table)).toString();
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
				srcFileSet.add(tempDir.relativize(tmpFilePath).toString());
				Files.write(tmpFilePath, gson.toJson(row).getBytes());
			}
		}
	}
	
	private void syncFile(BackupRepository repo,Set<String> srcFile, Path tmpDir) throws IOException {
		Set<String> filesInRepo = repo.getFiles();
		for(String file:srcFile) {
			//create file either it is new file or there is missmatch in the content.
			if(!filesInRepo.contains(file) 
					|| Files.mismatch(Path.of(tmpDir.toString(), file), repo.getFileContent(file))!=-1) {
				repo.createOrReplaceFile(Path.of(tmpDir.toString(), file), file);
			}
		}
		
		//delete files if is removed.
		for(String file:filesInRepo) {
			if(!srcFile.contains(file)) {
				repo.deleteFile(file);
			}
		}
	}
	
}
