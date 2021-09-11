package com.databasecrud.databases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class HelperClass {
	private static  Logger 	logger = Logger.getLogger(ItemAMLDatabase.class.getName());
	public static final String FILENAME = "/home/dev/Downloads/TemplateProject/src/com/databasecrud/databases/database.properties";	 

	
	public static Properties load() throws IOException {
	    InputStream fis = null;
	    Properties props = null;
	    try {
	    	 props = new Properties();
	    	fis = new FileInputStream(FILENAME);
	        props.load(fis);
	      
	        
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception is: ", e);	    }   

	    return props;
	}
	protected static Connection getConnection() throws IOException {
		Connection connection=null;
		Properties props=  load();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.uName"), props.getProperty("jdbc.pass"));
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		catch(ClassNotFoundException e) {
			logger.error("Exception is: ", e);
		}
		return connection;
	}
}
