import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import common.*;

public class ServerGUI extends JFrame implements ActionListener, ChatIF {
	// Variables that represent various components of the server
	private JFrame mainProgram;
	
	// Menu bar and associated menus
	private JMenuBar mb;
	private JMenu file;
	private JMenu help;
	
	// Menu items for the server
	private JMenuItem about;
	private JMenuItem listenForConnections;
	private JMenuItem stopListening;
	private JMenuItem exitProgram;
	
	// Variables for contact dialoog and log in
	private JDialog contactInfo;
	private JButton closeAbout;
	private JDialog loginBox;
	
	// Sending message variables
	private JTextField message;
	private JLabel messageLabel;
	private JButton sendButton;
	
	// Chat box and connection status
	private JTextArea chatBox;
	private JLabel connectionStatus, date;

	// Create an instance of EchoServer and default port
	EchoServer server;
	static int DEFAULT_PORT = 5555;

	// Function for displaying messages
	public void display(String message) 
  	{
    		if (message.contains("#server")) {
			// Remove #server and display server's maessage
			int index = message.indexOf('#');
			String mes = message.substring(0, index);
			chatBox.append("SERVER MSG> " + mes + "\n");
		} 
		else {
			// Split the message and print: loginID> msg (messages)
			if (message.contains(" has loged on")) {
				String[] mes = message.split(" ", 2);
				chatBox.append(mes[0] + " " + mes[1] + "\n");
			}
			else {
				String[] mes = message.split(" ", 2);
				chatBox.append(mes[0] + "> " + mes[1] + "\n");
			}
		}
    		
  	}
	
	public ServerGUI(int port) {
		// Start server
		server = new EchoServer(port);

		/* Name the program's frame and set its size */
		mainProgram = new JFrame("SimpleChatServer");
		mainProgram.setLayout(null);
		mainProgram.setSize(630, 500);
		
		// Add a label, text area, and button for sending messages
		message = new JTextField();
		message.setBounds(10,380,240,50);
		
		messageLabel = new JLabel("Type your message here: ");
		messageLabel.setBounds(10,350,200,20);
		
		sendButton = new JButton("Send");
		sendButton.setBounds(300,380,100,20);
		
		mainProgram.add(message);
		mainProgram.add(messageLabel);
		mainProgram.add(sendButton);
		
		// Add event listener for the sendButton
		sendButton.addActionListener(this);
		
		// Add a chat box
		chatBox = new JTextArea("\n");
		chatBox.setBounds(10, 0, 300, 300);
		chatBox.setEditable(true);
		mainProgram.add(chatBox);
		
		// Add labels that indicate current date and connectivity status
		connectionStatus = new JLabel("You're not logged in! To login, go to File->Login");
		connectionStatus.setBounds(320,0,275,50);
		mainProgram.add(connectionStatus);
		
		// Add a menu bar
		mb = new JMenuBar();
			
		// Create menus that appear in menu bar
		file = new JMenu("File");
		help = new JMenu("Help");
			
		// In Help menu, user can get version information and contact details 
		about = new JMenuItem("About");
		help.add(about);
		
		// Add options for logging in, logging out and closing the program
		listenForConnections = new JMenuItem("Start Listening");
		stopListening = new JMenuItem("Stop Listening");
		exitProgram = new JMenuItem("Exit");
		file.add(listenForConnections);
		file.add(stopListening);
		file.add(exitProgram);
		
		// Create a dialog box for contact information and version history
		contactInfo = new JDialog(mainProgram);
        contactInfo.setTitle("About SimpleChatGUI Server.");
        contactInfo.setSize(new Dimension(850, 100));
		
		// Add elements to the dialog (description and button)
		JPanel pan=new JPanel();
		pan.setLayout(new FlowLayout());
		pan.add(new JLabel("Simple Chat Server GUI is licensed under MIT license."));
		pan.add(new JLabel("Furthermore, OCSF component of Simple Chat was made at University of Ottawa"));
		
		// Add action listener to the close button
		closeAbout = new JButton("Close");
		pan.add(closeAbout);
		closeAbout.addActionListener(this);
		
		// Add the panel to the dialog
		contactInfo.add(pan);
		
		// Location and visibility
        contactInfo.setLocationRelativeTo(mainProgram);
        contactInfo.setModal(false);
        contactInfo.setVisible(false);
			
		// Add action listeners to the menu items
		about.addActionListener(this);
		listenForConnections.addActionListener(this);
		stopListening.addActionListener(this);
		exitProgram.addActionListener(this);

		// Add menus to the menu bar. Then, add the menu and panel bar to the frame
		mb.add(file);
		mb.add(help);
		mainProgram.setJMenuBar(mb);
		//mainProgram.add(panel);
		
		// Operations for closing the server and its display
		mainProgram.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainProgram.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==about) {
			// Display the dialog box
			contactInfo.setVisible(true);
		}
		if (e.getSource()==closeAbout) {
			// Close the box
			contactInfo.setVisible(false);
		}
		if (e.getSource()==exitProgram) {
			// Exit the program
			System.exit(0);
		}
		if (e.getSource()==sendButton) {
			// Send the message to the server
			String out = message.getText();
			server.handleInputFromServerConsole(out);
			// Clean the message box and display the message into the chat box
			message.setText(null);
			chatBox.append(out+"\n");
		}
		if (e.getSource()==listenForConnections) {
			// Send command for listening
			server.handleInputFromServerConsole("#start");
			// Change connection status
			connectionStatus.setText("You're listening! Go to File->stop to stop.");
		}
		if (e.getSource()==stopListening) {
			// Stop listening for new connections
			server.handleInputFromServerConsole("#stop");
			// Change connection status
			connectionStatus.setText("You're not listening! To listen, go to File->listen");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int port = 0; //Port to listen on

				    try
				    {
				      port = Integer.parseInt(args[0]); //Get port from command line
				    }
				    catch(Throwable t)
				    {
				      port = DEFAULT_PORT; //Set port to 5555
				    }
					
				    ServerGUI serv = new ServerGUI(port);
				  }
		});
	}
}
