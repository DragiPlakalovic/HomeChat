import java.sql.*; 	// For communicating with SQL database
import java.io.*;	// Configuration File Access

public class SessionStoring {
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
	public SessionStoring(int Limit) {
		try {
			// Initialize a connection to the SQL Server
			System.out.print("Connecting to the server...");
			Class.forName("com.mysql.jdbc.Driver"); // Load Driver class
			cnt = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			// Set the limit for sessions per table
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
			insertQuery = "INSERT INTO " + tb.getCurrentSessionTable() + " (userID, sessionID) VALUES (\'dragi17\', ?);";
			insert = cnt.prepareStatement(insertQuery);
			selectQuery = "SELECT COUNT(*) FROM " + tb.getCurrentSessionTable();
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
		db.executeQuery("USE Test;");
	}
	
	// Method that will insert data into the table
	public void insertSession(String session) throws SQLException, IOException {
		// Insert session into the table
		insert.setString(1, session);
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
			tb.createNewSessionTableName();
			tb.updateSessionProperties();
			createQuery = "CREATE TABLE " + tb.getCurrentSessionTable() + " (userID varchar(255), sessionID varchar(255))";
			PreparedStatement create = cnt.prepareStatement(createQuery);
			create.executeUpdate();
			// Update where should sessions be stored
			insertQuery = "INSERT INTO " + tb.getCurrentSessionTable() + " (userID, sessionID) VALUES (\'dragi17\', ?);";
			insert = cnt.prepareStatement(insertQuery);
		}
		System.out.println(currentNo);
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
			SessionStoring test = new SessionStoring(5);
			// Insert data
			test.insertSession("Test");
			// Close connection
			test.closeAndDestroy();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}