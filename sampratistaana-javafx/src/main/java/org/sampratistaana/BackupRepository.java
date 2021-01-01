package org.sampratistaana;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * This is backup repository contract between application and backup repository. Instance of implementation must be
 * thread safe. 
 * 
 * Same instance will be shared with multiple threads and also there will be heavy IO traffic between the application
 * and repository. Hence it is better to do implement right performance optimization.
 *
 */
public interface BackupRepository {
	
	/**
	 * Initialization of the repository. It is called when new instance of object is created.
	 * This method could be used perform clone of remote repository.
	 * @throws IOException
	 */
	void init() throws IOException;
	
	/**
	 * Get the all files in the repository. All the path must be relative to repository root.
	 * @return Collections of all files available in the repository
	 * @throws IOException
	 */
	Set<String> getFiles() throws IOException;
	
	/**
	 * Read the file from the disk and replace the file or create new version of the file in the repository
	 * @param srcFile File in the disk that need to be replaced
	 * @param relativePathInRepo Destination file path relative to repository root.
	 * @throws IOException
	 */
	void createOrReplaceFile(Path srcFile,String relativePathInRepo) throws IOException;
	
	/**
	 * Delete the file in the repository
	 * @param relativePathInRepo relative path of the file from the reposiroty root that need to be deleted
	 * @throws IOException
	 */
	void deleteFile(String relativePathInRepo) throws IOException;
	
	/**
	 * Download the file content from the repository to local disk. In the implementation, it is better to keep the connection open
	 * for future downloads.
	 * @param relativePathInRepo
	 * @return
	 */
	Path getFileContent(String relativePathInRepo) throws IOException;
}
