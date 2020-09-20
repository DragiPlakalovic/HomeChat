import java.sql.*; 	// For communicating with SQL database
import java.io.*;	// Configuration File Access

public class MessageStoring {
	// Instance variables
	private Connection cnt;
	private PreparedStatement insert;
	private PreparedStatement select;
	private TableNameGenerator tb;
	private BufferedReader br;
	// Limit variable
	private int limit;
	
	// Variables that will store queries for: insertion, selection, and creation of tables
	private String createQuery;
	private String selectQuery;
	private String insertQuery;
	
	// Constructor 
	public MessageStoring(int Limit) {
		try {
			// Initialize a connection to the SQL Server
			System.out.print("Connecting to the server...");
			Class.forName("com.mysql.jdbc.Driver"); // Load Driver class
			cnt = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			// Set the limit for messages per table
			limit = Limit;
			// Select database
			selectDatabase();
			// Announce success
			System.out.println("Done");
			// Start Table Name generator
			tb = new TableNameGenerator("config.properties");
			// Start buffer reader
			br = new BufferedReader(new InputStreamReader(System.in));
			// Initialize a prepared statements
			insertQuery = "INSERT INTO " + tb.getCurrentMessagingTable() + " (userID, sessionID, DateTime, message) VALUES (?, ?, ?, ?);";
			insert = cnt.prepareStatement(insertQuery); 
			selectQuery = "SELECT COUNT(*) FROM " + tb.getCurrentMessagingTable();
			select = cnt.prepareStatement(selectQuery);
		}
		// Handle all exceptions that may occur
		catch (Exception e) {
			System.out.println("Error!");
			System.out.println(e);
		}
	}
	
	// Method that selects which database to use
	private void selectDatabase() throws SQLException {
		Statement db = cnt.createStatement();
		db.executeQuery("USE HomeChat;");
	}
	
	// Method that will insert data into the table
	public void insertMessage(String userID, String sessionID, String TimeDate, String message) throws SQLException, IOException {
		// Insert message into the table
		insert.setString(1,userID);
		insert.setString(2, sessionID);
		insert.setString(3, TimeDate);
		insert.setString(4, message);
		insert.executeUpdate();
		// Check if there is a need for a new table
		ResultSet rs = select.executeQuery();
		int currentNo = 0;
		while (rs.next()) {
			currentNo = rs.getInt(1);
		}
		// Check if there is a need for creating new table
		if (currentNo >= limit) {
			// If so, create new table name and table itself
			tb.createNewMessagingTableName();
			tb.updateMessagingProperties();
			createQuery = "CREATE TABLE " + tb.getCurrentMessagingTable() + " (userID varchar(255), sessionID varchar(255), DateTime varchar(255), message varchar(255))";
			PreparedStatement create = cnt.prepareStatement(createQuery);
			create.executeUpdate();
			// Update where should messages be stored
			insertQuery = "INSERT INTO " + tb.getCurrentMessagingTable() + " (userID, sessionID, DateTime, message) VALUES (?, ?, ?, ?)";
			insert = cnt.prepareStatement(insertQuery);
		}
	}
	
	// Method for creating additional tables
	
	// Closes connection to the SQL server and performs clean-up.
	public void closeAndDestroy() {
		// Try closing the connection
		try {
			System.out.print("Closing connection to the server...");
			// Close cnt if it exists
			if (cnt != null)
				cnt.close();
			System.out.println("Done");
		}
		// Handle any exception that may arise during connection closing
		catch (Exception e) {
			System.out.println("Error");
			System.out.println(e);
		}
	}
	
	
	// DRIVER CODE!
	public static void main(String[] args) {
		try {
			// Start connection
			MessageStoring test = new MessageStoring(5);
			// Insert data
			test.insertMessage("dragi", "blah", "345435", "Test");
			// Close connection
			test.closeAndDestroy();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}