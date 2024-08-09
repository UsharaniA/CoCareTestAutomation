package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author usharani A
 *
 */
public class EnvHelper {

	public static Properties prop = new Properties();

	static {
		try {	

			loadPropertiesFromLocalConfig ();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static String getValue(String key) {
//		System.out.println(key + " : " +  prop.getProperty(key));
		return prop.getProperty(key);
	}

	
	
	private static void loadProperties (InputStream inputStream) throws IOException {
		prop.putAll(prop);
//		System.out.println(prop);
		prop.load(inputStream);
	}
//	
	private static void loadPropertiesFromLocalConfig () {
		try {
			
			List<File> propertyFiles = FileUtility.getFilesList(".", new String[]{"properties"}, true);

			for (File file : propertyFiles) {
//				System.out.println("propertyfile" + file);
			
			FileInputStream inputStream = new FileInputStream(file);
			loadProperties(inputStream);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			
		}
	}
	
	
}
