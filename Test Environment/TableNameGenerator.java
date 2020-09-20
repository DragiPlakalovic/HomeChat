import java.util.*;
import java.io.*;

public class TableNameGenerator {
	// Variable that stores the name of .properties file and associated Properties object
	private String propFileName;
	private Properties prop;
	
	// Store the names of tables that are in the database for HomeChat
	private String[] messageTablesList;
	private String[] sessionTablesList;
	private String messageTablesString;
	private String sessionTablesString;
	private Integer messageTableNumber;
	private Integer sessionTableNumber;
	
	// Variables that hold current and last-used table for sessions and messaging;
	private String currentMessageTable;
	private String lastMessageTable;
	private String currentSessionTable;
	private String lastSessionTable;
 
	public TableNameGenerator(String propFileName) throws IOException
	{
		// Get the file name
		this.propFileName = propFileName;
		// Load properties
		loadProperties();
		// Set the message and session tables for use
		updateMessagingTableNameUse();
		updateSessionTableNameUse();
	}
	
	// Method for updating current and previous table for messaging
	public void updateMessagingTableNameUse() {
		// If List of message tables for messaging has 2 or more members
		if (messageTablesList.length >= 2) {
			// Assign last and second last table names as current and previous table names
			int lastIndex = messageTablesList.length - 1;
			int secondLastIndex = lastIndex - 1;
			lastMessageTable = messageTablesList[secondLastIndex];
			currentMessageTable = messageTablesList[lastIndex];
		}
		// If there is only one entry for messaging
		if (messageTablesList.length <= 1) {
			// You have only current table
			lastMessageTable = null;
			currentMessageTable = messageTablesList[0];
		}
	}
	
	// Method for updating current and previous table sessions
	public void updateSessionTableNameUse() {
		// If List of session tables for sessions has 2 or more members
		if (sessionTablesList.length >= 2) {
			// Assign last and second last table names as current and previous table names
			int lastIndex = sessionTablesList.length - 1;
			int secondLastIndex = lastIndex - 1;
			lastSessionTable = sessionTablesList[secondLastIndex];
			currentSessionTable = sessionTablesList[lastIndex];
		}
		// If there is only one entry for sessions
		if (sessionTablesList.length <= 1) {
			// You have only current table
			lastSessionTable = null;
			currentSessionTable = sessionTablesList[0];
		}
	}
	
	// This method will create messaging table name and will change which table name is in use
	public void createNewMessagingTableName() {
		// Add new messaging table name
		messageTablesString = String.join(",", messageTablesList);
		messageTableNumber++;
		messageTablesString += (",message" + (messageTableNumber + ""));
		// Update the name of table in use
		updateMessagingTableNameUse();
		currentMessageTable = ("message" + (messageTableNumber + ""));
	}
	
	// This method will create session table name and will change which table name is in use
	public void createNewSessionTableName() {
		// Add new session table name
		sessionTablesString = String.join(",", sessionTablesList);
		sessionTableNumber++;
		sessionTablesString += (",session" + (sessionTableNumber + ""));
		// Update the name of table in use
		updateSessionTableNameUse();
		currentSessionTable = ("session" + (sessionTableNumber + ""));
	}
	
	// Method for loading all properties
	public void loadProperties() throws IOException {
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		// Raise an exception if cannot locate config.properties
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
			
		// Get the lists of tables and their numbers
		messageTableNumber = Integer.parseInt(prop.getProperty("messageTableNumber"));
		messageTablesList = prop.getProperty("messageTables").split(",");
		sessionTableNumber = Integer.parseInt(prop.getProperty("sessionTableNumber"));
		sessionTablesList = prop.getProperty("sessionTables").split(",");
		
		// Close the input stream
		inputStream.close();
	}
	
	// Method for updating config.properties for messages
	public void updateMessagingProperties() throws IOException {
		// Update properties
		prop.setProperty("messageTableNumber", (messageTableNumber+""));
		prop.setProperty("messageTables", messageTablesString);
			
		// Save to the file
		FileOutputStream fout = new FileOutputStream(propFileName);
		prop.save(fout, "Added more tables");
		fout.close();
	}
	
	// Method for updating config.properties for messages
	public void updateSessionProperties() throws IOException {
		// Update properties
		prop.setProperty("sessionTableNumber", (sessionTableNumber+""));
		prop.setProperty("sessionTables", sessionTablesString);
			
		// Save to the file
		FileOutputStream fout = new FileOutputStream(propFileName);
		prop.save(fout, "Added more tables");
		fout.close();
	}
	
	// Add accessor method for current and previous tables for messaging and sessions
	public String getCurrentMessagingTable() {return currentMessageTable;}
	public String getCurrentSessionTable() {return currentSessionTable;}
	
	public static void main(String[] args) throws IOException {
		try {
			TableNameGenerator test = new TableNameGenerator("config.properties");
			test.loadProperties();
			test.createNewMessagingTableName();
			test.createNewSessionTableName();
			test.updateMessagingProperties();
			test.updateSessionProperties();
			System.out.println(test.getCurrentMessagingTable());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}