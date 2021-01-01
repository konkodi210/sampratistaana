package org.sampratistaana;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * One drive implementation of repository
 *
 */
public class OneDriveBackupRepository implements BackupRepository {
	//it is immutable aka thread safe. Hence it can be shared/re-used
	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
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
					fileSet.add(Path.of(repositoryPath).relativize(file).toString());
				}
				return super.visitFile(file, attrs);
			}
		});
		return fileSet;
	}

	@Override
	public void createOrReplaceFile(Path srcFile, String relativePathInRepo) throws IOException {
		Files.move(srcFile, getAbsolutePath(relativePathInRepo), StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public void deleteFile(String relativePathInRepo) throws IOException {
		//Do the soft delete file by moving into deleted folder		
		Path targetFile = Path.of(repositoryPath, "deleted",relativePathInRepo);
		if(Files.exists(targetFile)) {
			targetFile=Path.of(targetFile.toString()+"_"+fmt.format(LocalDateTime.now()));
		}else {
			Files.createDirectories(targetFile.getParent());
		}

		Files.move(getAbsolutePath(relativePathInRepo), targetFile,StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public Path getFileContent(String relativePathInRepo) {		
		return getAbsolutePath(relativePathInRepo);
	}

	private Path getAbsolutePath(String relativePathInRepo) {
		return Path.of(repositoryPath, relativePathInRepo);
	}

}
