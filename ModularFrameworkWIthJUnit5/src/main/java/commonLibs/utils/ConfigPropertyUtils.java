package commonLibs.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertyUtils {
	
	public static Properties configFileReader(String configFilename) throws Exception{
		configFilename = configFilename.trim();
		
		Properties property = new Properties();
		
		InputStream inStream = new FileInputStream(configFilename);
		
		property.load(inStream);
		
		return property;
	}

}
