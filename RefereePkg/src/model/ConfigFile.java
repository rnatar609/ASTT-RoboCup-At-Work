package model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFile
{
	private static String configFileFullName; // = new String( System.getenv( "TASK_SERVER_CONFIG_DIR" ) + File.separator + "config.txt") ;   
	private Properties tripletProperties;

	public ConfigFile()
	{
		tripletProperties = new Properties();
	}
	
	public boolean setConfigFile(File fileObj)
	{
		configFileFullName = new String(fileObj.getAbsolutePath());
		if (!fileObj.exists()) 
			return false;
		System.out.println("config file " + fileObj.getAbsolutePath() + " exists.");
		return true;
	}
	
	public void loadProperties() throws Exception
	{
		try
		{			
			FileInputStream in = new FileInputStream( ConfigFile.configFileFullName );
			tripletProperties.load( in );
			in.close();			
		} catch ( Exception e )
		{
			System.out.println( "Exception in ConfigFile loadProperties: " + e.getMessage() );
			throw e;
		}
	}

	public Properties getProperties()
	{
		return tripletProperties;
	}
}

