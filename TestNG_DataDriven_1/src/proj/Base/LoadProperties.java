package proj.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



/**
 * This Class loads the different properties file that we need and puts them
 * into a hash map .Some of the entries in the hash map are used in
 * 
 * 
 */
public class LoadProperties {

	private static Properties properties;
	private static LoadProperties cfg = null;
	
	private static Map<String, String> propMap = new HashMap<String, String>();

	/**
	* Constructor
	*/
	public LoadProperties() {
		
		// TO LOAD  GENERAL PROPERTIES FILE. 
		
		loadPropertiesFile("ObjectRepository");
	}

	/**
	* Loads the properties file in the constructor
	* 
	* @param fileName
	*/
	private void loadPropertiesFile(String fileName) {
		try {
			FileReader reader = new FileReader(new File(System.getProperty("user.dir")+"//src//proj//Configuration//"+fileName+".properties"));
			properties = new Properties();
			properties.load(reader);
			reader.close();
			Enumeration<?> enums = properties.propertyNames();

			while (enums.hasMoreElements()) {
				Object c = enums.nextElement();
				propMap.put((String) c, (String) properties.get(c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	* Returns the value of a key from the hash map
	* 
	* @param keyName
	* @return value of the key.
	*/
	public String getKey(String keyName) {
		return propMap.get(keyName);
	}

	/**
	* 
	* 
	* @return handler to this class.
	*/
	public LoadProperties getConfigProperties() {
		if (cfg == null) {
			cfg = new LoadProperties();
		}
		return cfg;
	}

}
