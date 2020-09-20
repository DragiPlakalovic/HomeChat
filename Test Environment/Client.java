import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Dragi Plakalovic
 */
public class Client extends JFrame {
	public Client() throws Exception {
		// Start an instance for SQL and client
		sql = new LoginVerifier();
		// Start a client
		initComponents();
		// Configure close operation and visibility
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Create a function that will generate error messages if something goes wrong
	public void generateCreateUserErrorMessage(String cause) {
		JOptionPane.showMessageDialog(dialog1, cause, "Error Creating User Account", JOptionPane.ERROR_MESSAGE);
	}
	public void generateLoginUserErrorMessage(String cause) {
		JOptionPane.showMessageDialog(dialog2, cause, "Error Logging in!", JOptionPane.ERROR_MESSAGE);
	}

	private void menuItem1ActionPerformed(ActionEvent e) {
		dialog1.setVisible(true);
	}
	
	// Method that tests if password and confirm password are the same
	private void checkPasswords() {
		// Get passwords
		String initial = new String(passwordField1.getPassword());
		String verify = new String(passwordField3.getPassword());
		// Check if the passwords are the same
		if (!(initial.equals(verify))) {
			// There is an error
			generateCreateUserErrorMessage("Passwords entered do not match");
		}
	}
	
	// Method for cleaning up text fields and closing dialogs when user clicks on buttons in the dialog
	private void createUserCleanAndClose() {
		// Close the dialog and clear text fields
		dialog1.setVisible(false);
		userName.setText("");
		EMAIL.setText("");
		passwordField1.setText("");
		passwordField3.setText("");
	}
	
	// Method for cleaning and closing Login dialog
	private void loginUserCleanAndClose() {
		// Clean the text fields and close the dialog
		dialog2.setVisible(false);
		userName2.setText("");
		passwordField2.setText("");
	}
	
	private void createButtonActionPerformed(ActionEvent e) {
		// Check if passwords are OK.
		checkPasswords();
		// Create user
		sql.createAccount(getUserNameFromCreate(), getPasswordFromCreate(), getEmail(), this);
		// Start client
		client = new HomeChatClient(getHost(), 5555, this);
		// Close the dialog
		createUserCleanAndClose();
	}
	
	private void loginButtonActionPerformed(ActionEvent e) {
		// Check if credentials are valid
		sql.loginUser(getUserNameFromLogin(), getPasswordFromLogin(), this);
		// Close the dialog
		loginUserCleanAndClose();
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		// Call method for cleanup
		createUserCleanAndClose();
	}

	private void menuItem2ActionPerformed(ActionEvent e) {
		// Activate login dialog
		dialog2.setVisible(true);
	}

	private void menuItem3ActionPerformed(ActionEvent e) {
		System.exit(0);
	}

	private void cancelButton2ActionPerformed(ActionEvent e) {
		// Clean and close the dialog
		loginUserCleanAndClose();
	}
	
	// Methods for obtaining user name values entered
	public String getUserNameFromCreate() {
		return userName.getText();
	}
	public String getUserNameFromLogin() {
		return userName2.getText();
	}
	
	// Methods for obtaining passwords
	public String getPasswordFromCreate() {
		return new String(passwordField1.getPassword());
	}
	public String getPasswordFromLogin() {
		return new String(passwordField2.getPassword());
	}
	
	// Method to get email address and host
	public String getEmail() {
		return EMAIL.getText();
	}
	public String getHost() {
		return hostName.getText();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Dragi
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem2 = new JMenuItem();
		menuItem3 = new JMenuItem();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		scrollPane2 = new JScrollPane();
		textArea2 = new JTextArea();
		label1 = new JLabel();
		sendButton = new JButton();
		scrollPane3 = new JScrollPane();
		textArea3 = new JTextArea();
		label3 = new JLabel();
		dialog1 = new JDialog();
		userID = new JLabel();
		userName = new JTextField();
		eMail = new JLabel();
		EMAIL = new JTextField();
		passwordField1 = new JPasswordField();
		password = new JLabel();
		verifyPassword = new JLabel();
		createButton = new JButton();
		cancelButton = new JButton();
		passwordField3 = new JPasswordField();
		dialog2 = new JDialog();
		label5 = new JLabel();
		password2 = new JLabel();
		userName2 = new JTextField();
		passwordField2 = new JPasswordField();
		loginButton = new JButton();
		cancelButton2 = new JButton();
		hostName = new JTextField();
		label2 = new JLabel();

		//======== this ========
		setTitle("Home Chat");
		setMinimumSize(new Dimension(586, 380));
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("User Connection");

				//---- menuItem1 ----
				menuItem1.setText("Create User Account");
				menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
				menu1.add(menuItem1);

				//---- menuItem2 ----
				menuItem2.setText("Login with Existing Account");
				menuItem2.addActionListener(e -> menuItem2ActionPerformed(e));
				menu1.add(menuItem2);

				//---- menuItem3 ----
				menuItem3.setText("Sign Out");
				menuItem3.setEnabled(false);
				menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
				menu1.add(menuItem3);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(textArea1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(405, 40, 150, 265);

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(textArea2);
		}
		contentPane.add(scrollPane2);
		scrollPane2.setBounds(10, 40, 385, 205);

		//---- label1 ----
		label1.setText("Other clients available");
		contentPane.add(label1);
		label1.setBounds(405, 10, 150, 25);

		//---- sendButton ----
		sendButton.setText("Send");
		contentPane.add(sendButton);
		sendButton.setBounds(345, 255, sendButton.getPreferredSize().width, 50);

		//======== scrollPane3 ========
		{
			scrollPane3.setViewportView(textArea3);
		}
		contentPane.add(scrollPane3);
		scrollPane3.setBounds(10, 255, 330, 50);

		//---- label3 ----
		label3.setText("Message Chat");
		contentPane.add(label3);
		label3.setBounds(10, 10, 100, 25);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());

		//======== dialog1 ========
		{
			dialog1.setTitle("Create New Account");
			dialog1.setMinimumSize(new Dimension(305, 237));
			Container dialog1ContentPane = dialog1.getContentPane();
			dialog1ContentPane.setLayout(null);

			//---- userID ----
			userID.setText("User name:");
			dialog1ContentPane.add(userID);
			userID.setBounds(15, 15, 70, 25);
			dialog1ContentPane.add(userName);
			userName.setBounds(110, 15, 170, 25);

			//---- eMail ----
			eMail.setText("Email:");
			dialog1ContentPane.add(eMail);
			eMail.setBounds(15, 50, 65, 25);
			dialog1ContentPane.add(EMAIL);
			EMAIL.setBounds(110, 50, 170, 25);
			dialog1ContentPane.add(passwordField1);
			passwordField1.setBounds(110, 85, 170, 25);

			//---- password ----
			password.setText("Password");
			dialog1ContentPane.add(password);
			password.setBounds(15, 85, 60, 25);

			//---- verifyPassword ----
			verifyPassword.setText("Confirm Password");
			dialog1ContentPane.add(verifyPassword);
			verifyPassword.setBounds(10, 120, 90, 30);

			//---- createButton ----
			createButton.setText("Create");
			createButton.addActionListener(e -> createButtonActionPerformed(e));
			dialog1ContentPane.add(createButton);
			createButton.setBounds(60, 195, 75, 30);

			//---- cancelButton ----
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
			dialog1ContentPane.add(cancelButton);
			cancelButton.setBounds(155, 195, 80, 30);
			dialog1ContentPane.add(passwordField3);
			passwordField3.setBounds(110, 120, 170, 25);
			
			//---- label2 ----
			label2.setText("Host");
			dialog1ContentPane.add(label2);
			label2.setBounds(10, 155, 90, 25);
			dialog1ContentPane.add(hostName);
			hostName.setBounds(110, 155, 170, 25);
			
			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < dialog1ContentPane.getComponentCount(); i++) {
					Rectangle bounds = dialog1ContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = dialog1ContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				dialog1ContentPane.setMinimumSize(preferredSize);
				dialog1ContentPane.setPreferredSize(preferredSize);
			}
			dialog1.pack();
			dialog1.setLocationRelativeTo(dialog1.getOwner());
		}

		//======== dialog2 ========
		{
			dialog2.setTitle("Login as existing user");
			Container dialog2ContentPane = dialog2.getContentPane();
			dialog2ContentPane.setLayout(null);

			//---- label5 ----
			label5.setText("User name:");
			dialog2ContentPane.add(label5);
			label5.setBounds(10, 20, 70, 25);

			//---- password2 ----
			password2.setText("Password");
			dialog2ContentPane.add(password2);
			password2.setBounds(10, 50, 60, 25);
			dialog2ContentPane.add(userName2);
			userName2.setBounds(85, 20, 170, 25);
			dialog2ContentPane.add(passwordField2);
			passwordField2.setBounds(85, 50, 170, 25);

			//---- loginButton ----
			loginButton.setText("Login");
			loginButton.addActionListener(e -> loginButtonActionPerformed(e));
			dialog2ContentPane.add(loginButton);
			loginButton.setBounds(40, 110, 75, 30);

			//---- cancelButton2 ----
			cancelButton2.setText("Cancel");
			cancelButton2.addActionListener(e -> cancelButton2ActionPerformed(e));
			dialog2ContentPane.add(cancelButton2);
			cancelButton2.setBounds(140, 110, 80, 30);

			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < dialog2ContentPane.getComponentCount(); i++) {
					Rectangle bounds = dialog2ContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = dialog2ContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				dialog2ContentPane.setMinimumSize(preferredSize);
				dialog2ContentPane.setPreferredSize(preferredSize);
			}
			dialog2.pack();
			dialog2.setLocationRelativeTo(dialog2.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// Variables for parts of the client
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JScrollPane scrollPane2;
	private JTextArea textArea2;
	private JLabel label1;
	private JButton sendButton;
	private JScrollPane scrollPane3;
	private JTextArea textArea3;
	private JLabel label3;
	private JDialog dialog1;
	private JLabel userID;
	private JTextField userName;
	private JLabel eMail;
	private JTextField EMAIL;
	private JPasswordField passwordField1;
	private JLabel password;
	private JLabel verifyPassword;
	private JButton createButton;
	private JButton cancelButton;
	private JPasswordField passwordField3;
	private JDialog dialog2;
	private JLabel label5;
	private JLabel password2;
	private JTextField userName2;
	private JPasswordField passwordField2;
	private JButton loginButton;
	private JButton cancelButton2;
	private JTextField hostName;
	private JLabel label2;
	
	// Functional variables
	private LoginVerifier sql;
	private HomeChatClient client;
	
	public static void main(String[] args) {
		try {
			Client instance = new Client();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
