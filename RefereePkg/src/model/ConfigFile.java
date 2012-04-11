package model;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFile
{
	static final String configFileFullName = new String( System.getenv( "TASK_SERVER_CONFIG_DIR" ) + File.separator + "config.txt") ;   
	Properties tripletProperties;

	public ConfigFile()
	{
		tripletProperties = new Properties();
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

