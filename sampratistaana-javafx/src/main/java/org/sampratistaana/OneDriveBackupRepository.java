package org.sampratistaana;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
		Path repoPath = Path.of(repositoryPath);
		return Files.walk(repoPath)
				.filter(path -> Files.isRegularFile(path))
				.map(path -> repoPath.relativize(path).toString())
				.collect(Collectors.toSet());
	}

	@Override
	public void createOrReplaceFile(Path srcFile, String relativePathInRepo) throws IOException {
		Files.move(srcFile, getAbsolutePath(relativePathInRepo),REPLACE_EXISTING);
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

		Files.move(getAbsolutePath(relativePathInRepo), targetFile,REPLACE_EXISTING);
	}

	@Override
	public Path getFileContent(String relativePathInRepo) {		
		return getAbsolutePath(relativePathInRepo);
	}

	private Path getAbsolutePath(String relativePathInRepo) {
		return Path.of(repositoryPath, relativePathInRepo);
	}

}
