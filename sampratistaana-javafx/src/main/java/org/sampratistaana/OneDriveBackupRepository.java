package org.sampratistaana;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * One drive implementation of repository
 *
 */
public class OneDriveBackupRepository implements BackupRepository {
	private String repositoryPath;

	@Override
	public void init() throws IOException {
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			repositoryPath=prop.getProperty("onedrive.dir");
		}catch(FileNotFoundException e) {
			repositoryPath=Paths.get(System.getProperty("user.home"), "samprathistana_backup").toString();
		}
	}

	@Override
	public Set<String> getFiles() throws IOException {
		final Set<String> fileSet=new HashSet<>();
		Files.walkFileTree(Paths.get(repositoryPath), new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if(attrs.isRegularFile()) {
					fileSet.add(file.toString());
				}
				return super.visitFile(file, attrs);
			}
		});
		return fileSet;
	}

	@Override
	public void createOrReplaceFiles(Path srcFile, String relativePathInRepo) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFile(String relativePathInRepo) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Path getFileContent(String relativePathInRepo) {
		// TODO Auto-generated method stub
		return null;
	}

}
