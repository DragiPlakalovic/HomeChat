import java.sql.*;

public class LoginVerifier {
	private Connection cnt;
	private PreparedStatement createUser;
	private PreparedStatement loginUser;
	
	// Start database driver and prepare statements.
	public LoginVerifier() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		cnt = DriverManager.getConnection("jdbc:mysql://localhost:3306/HomeChat", "root", "");
		createUser = cnt.prepareStatement("INSERT INTO Users (userID, password, email) VALUES (?, ?, ?);");
		loginUser = cnt.prepareStatement("SELECT * FROM Users WHERE userID = ? AND password = ?;");
	}
	
	// Method that creates an account
	public void createAccount(String getUserName, String password, String email, Client session) {
		try {
			// Add missing information into a statemet 
			createUser.setString(1, getUserName);
			createUser.setString(2, password);
			createUser.setString(3, email);
			// Execute update
			createUser.executeUpdate();
		}
		catch (SQLException e) {
			// If account exists...
			session.generateCreateUserErrorMessage("User account exists.\nTry to login as existing user.");
		}
	}
	
	public void loginUser(String getUserName, String password, Client session) {
		try {
			// Add missing information into a statemet 
			loginUser.setString(1, getUserName);
			loginUser.setString(2, password);
			// Execute update
			ResultSet rs = loginUser.executeQuery();
			if (!(rs.next()))
				session.generateLoginUserErrorMessage("User credentials invalid.\nPlease try again.");
		}
		catch (Exception e) {
			// If account does not exist or credentials are not valid
			System.out.println(e);
		}
	}
}