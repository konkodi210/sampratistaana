package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.getBackupService;
import static org.sampratistaana.ConnectionFactory.getConnection;
import static org.sampratistaana.ConnectionFactory.getDBFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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

	public void performBackup() {
		try {
			BackupRepository repo=getBackupService();
			String dbFile = Path.of("samprathistaana","sqlite","db_sampratistana.sqlite").toString();
			Path srcFile = Path.of(getDBFile());
			Path destFile=repo.getFileContent(dbFile);
			if(!Files.exists(srcFile) || !Files.exists(destFile)
					|| mismatch(srcFile, destFile)!=-1) {
				repo.deleteFile(dbFile);
				Path tmpFile=Files.createTempFile("db", ".sqlite");
				Files.copy(Path.of(getDBFile()), tmpFile, StandardCopyOption.REPLACE_EXISTING);
				repo.createOrReplaceFile(tmpFile,dbFile);
			}else {
				System.out.println("DB File same. Hence no change is needed");
				return;
			}
			
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

			syncFile(repo,srcFileSet,tempDir,dbFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
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

	private void syncFile(BackupRepository repo,Set<String> srcFile, Path tmpDir,String dbFilePath) throws IOException {
		Set<String> filesInRepo = repo.getFiles();
		for(String file:srcFile) {
			//create file either it is new file or there is missmatch in the content.
			if(!filesInRepo.contains(file) 
					|| mismatch(Path.of(tmpDir.toString(), file), repo.getFileContent(file))!=-1) {
				repo.createOrReplaceFile(Path.of(tmpDir.toString(), file), file);
			}
		}

		//delete files if is removed.
		for(String file:filesInRepo) {
			if(!file.equals(dbFilePath) && !file.startsWith("deleted") && !srcFile.contains(file)) {
				repo.deleteFile(file);
			}
		}
	}

	//For some reason maven compile was failing. Hence copy pasted the method from Java
	public static long mismatch(Path path, Path path2) throws IOException {
		final int BUFFER_SIZE = 8192;
		if (Files.isSameFile(path, path2)) {
			return -1;
		}
		byte[] buffer1 = new byte[BUFFER_SIZE];
		byte[] buffer2 = new byte[BUFFER_SIZE];
		try (InputStream in1 = Files.newInputStream(path);
				InputStream in2 = Files.newInputStream(path2);) {
			long totalRead = 0;
			while (true) {
				int nRead1 = in1.readNBytes(buffer1, 0, BUFFER_SIZE);
				int nRead2 = in2.readNBytes(buffer2, 0, BUFFER_SIZE);

				int i = Arrays.mismatch(buffer1, 0, nRead1, buffer2, 0, nRead2);
				if (i > -1) {
					return totalRead + i;
				}
				if (nRead1 < BUFFER_SIZE) {
					// we've reached the end of the files, but found no mismatch
					return -1;
				}
				totalRead += nRead1;
			}
		}
	}

}
