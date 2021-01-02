package org.sampratistaana;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.walk;
import static java.nio.file.Files.writeString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneDriveBackupRepositoryTest {
	private static ThreadLocal<String> tmpDir=new ThreadLocal<>();

	@Before
	public void setup() throws Exception{
		String dir=createTempDirectory("onedirve").toString();
		System.setProperty("ONEDRIVE_DIR", dir);
		writeString(Path.of(dir,"file1.json"), "file1");
		Path file2=Path.of(dir, "db","table1","tab1.json");
		createDirectories(file2.getParent());
		writeString(file2, "file2");
		tmpDir.set(dir);
	}

	@After
	public void tearDown() throws Exception{
		walk(Path.of(tmpDir.get()))
		.sorted(Comparator.reverseOrder())
		.map(Path::toFile)
		.forEach(File::delete);
	}

	private BackupRepository getRepo() throws Exception{
		OneDriveBackupRepository repo=new OneDriveBackupRepository();
		Field f=OneDriveBackupRepository.class.getDeclaredField("repositoryPath");
		f.setAccessible(true);
		f.set(repo, tmpDir.get());
		return repo;
	}

	@Test
	public void testGetFiles() throws Exception {
		assertThat("Match the files", getRepo().getFiles(),contains("file1.json","db/table1/tab1.json"));
	}

	@Test
	public void testCreateOrReplaceFiles() throws Exception {
		BackupRepository repo=getRepo();

		//check for new file
		Path file=createTempFile("newFile",".json");
		writeString(file,"newFile");
		repo.createOrReplaceFile(file, "newFile.json");
		assertThat("new File Created in Repository", repo.getFiles().contains("newFile.json"),equalTo(true));
		assertThat("File Content Matches", readAllBytes(repo.getFileContent("newFile.json")),equalTo("newFile".getBytes()));

		//check for updated file
		writeString(file,"updated");
		repo.createOrReplaceFile(file, "newFile.json");
		assertThat("File Content Matches", readAllBytes(repo.getFileContent("newFile.json")),equalTo("updated".getBytes()));
	}

	@Test
	public void testGetFileContent() throws Exception {
		BackupRepository repo=getRepo();
		assertThat("File Content Matches", readAllBytes(repo.getFileContent("file1.json")),equalTo("file1".getBytes()));
	}
	
	@Test
	public void testDeleteFile() throws Exception {
		getRepo().deleteFile("file1.json");
		assertThat("File must be moved to deleted Directory", exists(Path.of(tmpDir.get(), "deleted","file1.json")),equalTo(true));
	}
}
