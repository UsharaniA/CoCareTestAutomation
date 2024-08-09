package utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

import constants.EnvConstants;

/**
 * @author usharani A
 *
 */
public class FileUtility {

	/**
	 * This method provides the list of files in the sourcePath, supports for
	 * passing required extensions to be searched
	 * 
	 * @param sourceFolderPath
	 *            Source Folder to traverse for files.
	 * @param extensions
	 *            List of extensions to be considered while reading the list of
	 *            Files from Source Folder
	 * @param includeSubFolders
	 *            Should Include Sub Folders (true/false)
	 * @return List of Files
	 * 
	 */
	public static List<File> getFilesList(String sourceFolderPath, String[] extensions, boolean includeSubFolders) {

		File sourceFolder = new File(sourceFolderPath);

		List<File> files = null;

		if (null != extensions && extensions.length > 0) {
			files = (List<File>) FileUtils.listFiles(sourceFolder, extensions, includeSubFolders);
		} else {
			files = getDirectoryFiles(sourceFolder, includeSubFolders);
		}
		return files;
	}

	/**
	 * 
	 * @param sourceDir
	 *            - Source Folder to traverse for all files
	 * @param includeSubFolders
	 *            - Should Include Sub Folders
	 * @return List of Files
	 */
	public static List<File> getDirectoryFiles(File sourceDir, boolean includeSubFolders) {
		List<File> files = null;
		try {
			files = new ArrayList<File>();
			File[] sourceFolderFiles = sourceDir.listFiles();
			for (File file : sourceFolderFiles) {
				if (file.isDirectory()) {
					if (includeSubFolders) {
						getDirectoryFiles(file, includeSubFolders);
					}
				} else {
					files.add(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return files;
	}

	/**
	 * Creates a Directory(sub-directories)/Folder(s) in the Path.
	 * 
	 * @param directoryPath
	 *            Path for creation of the Folder(s)
	 * @return true/false for success/failure.
	 */
	public static boolean createDirectory(String directoryPath) {
		return new File(directoryPath).mkdirs();
	}

	/**
	 * Delete the file or Directory/Folder
	 * 
	 * @param file
	 *            File/Directory to be deleted.
	 */
	public static void deleteFileOrDirectory(File file) {
		if (file != null && !(file.getAbsolutePath().trim().equals(""))) {
			if (file.isDirectory()) {
				// directory is empty, then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				} else {
					// list all the directory contents
					String files[] = file.list();
					for (String temp : files) {
						// construct the file structure
						File fileDelete = new File(file, temp);
						// recursive delete
						deleteFileOrDirectory(fileDelete);
					}

					// check the directory again, if empty then delete it
					if (file.list().length == 0) {
						file.delete();
						System.out.println("Directory is deleted : " + file.getAbsolutePath());
					}
				}

			} else {
				// if file, then delete it
				file.delete();
				System.out.println("File is deleted : " + file.getAbsolutePath());
			}
		} else {
			System.out.println("File/Directory cannot be null or empty.");
		}

	}

	/**
	 * Returns a reference to a file with the specified name that is located on
	 * the classpath.
	 * 
	 * @param fileName
	 *            file name to be searched for in classpath
	 * @return File
	 */
	public static File findFileOnClassPath(final String fileName) {

		if (fileName != null && !fileName.trim().equals("")) {
			final String classpath = System.getProperty("java.class.path");
			final String pathSeparator = System.getProperty("path.separator");
			final StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);
			while (tokenizer.hasMoreTokens()) {
				final String pathElement = tokenizer.nextToken();
				final File directoryOrJar = new File(pathElement);
				final File absoluteDirectoryOrJar = directoryOrJar.getAbsoluteFile();
				if (absoluteDirectoryOrJar.isFile()) {
					final File target = new File(absoluteDirectoryOrJar.getParent(), fileName);
					if (target.exists()) {
						return target;
					}
				} else {
					final File target = new File(directoryOrJar, fileName);
					if (target.exists()) {
						return target;
					}
				}
			}
		} else {
			System.out.println("File Path cannot be null or empty.");
		}
		return null;
	}

	/**
	 * Provides the input stream searching the jar file for the file 
	 * eg:- getFileFromJarFile("log4j.properties");
	 * 
	 * @param fileName
	 *            File Name to search
	 * @return File
	 * @throws IOException
	 */
	public static File getFileFromJarFile(String fileName) throws IOException {
		File returnFile = new File(EnvHelper.getValue(EnvConstants.logsPath) + fileName);
		try {
			String path = EnvHelper.class.getResource("").getPath();
			path = path.substring(6, path.length() - 1);
			path = path.split("!")[0];
			JarFile jarFile = new JarFile(path);

			final Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry entry = entries.nextElement();
				if (entry.getName().contains((fileName))) {
					JarEntry fileEntry = jarFile.getJarEntry(entry.getName());
					FileUtils.copyInputStreamToFile(jarFile.getInputStream(fileEntry), returnFile);
					break;
				}
			}
			jarFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return returnFile;
	}

	/**
	 * Provides the input stream searching the jar file for the file eg:-
	 * getFileFromLocalClasspath("log4j.properties");
	 * 
	 * @param fileName
	 *            File Name to search
	 * @return File
	 * @throws IOException
	 */
	public static File getFileFromLocalClasspath(String fileName) throws IOException {
		File returnfile = null;
		try {
			List<File> files = FileUtility.getFilesList(".",
					new String[] { fileName.substring(fileName.lastIndexOf(".") + 1) }, true);
			for (File file : files) {
				if (file.getName().equals(fileName)) {
					returnfile = file;
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return returnfile;
	}
	
	
	 public static Path createResultFolder(String testcaseName) throws IOException {
//		 Path resultFolderPathFinal = null;  
		 Calendar now = Calendar.getInstance();
		String resultfolderrootpath= EnvHelper.getValue(EnvConstants.reportsPath);
		String resultfolderDate= resultfolderrootpath + DateTimeUtils.getDate();
		 String resultFolderPath = String.format("%s/%s_%d%d%d%d%d%d",				 
				    resultfolderDate,
	                testcaseName,
	                now.get(Calendar.MONTH) + 1,
	                now.get(Calendar.DATE),
	                now.get(Calendar.YEAR),
	                now.get(Calendar.HOUR_OF_DAY),
	                now.get(Calendar.MINUTE),
	                now.get(Calendar.SECOND));

	        Path resulttestFolderPath = Paths.get(resultFolderPath);
	        
	        if (!Files.exists(Paths.get(resultfolderDate))) {
	            Files.createDirectory(Paths.get(resultfolderDate));
	        }

//	        if (Files.exists(resultFolderDefault)) {
//	            FileUtils.deleteDirectory(resultFolderDefault.toFile());
//	        }

	        if (!Files.exists(resulttestFolderPath)) {
	            Files.createDirectory(resulttestFolderPath);	    
//	            resultFolderPathFinal = Paths.get(resulttestFolderPath);
	        }
//	        System.out.println(resulttestFolderPath);
	        return resulttestFolderPath;
	    }
	

}