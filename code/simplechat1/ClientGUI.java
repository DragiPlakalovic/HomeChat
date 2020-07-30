import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ClientGUI extends JFrame implements ActionListener {
	private JFrame mainProgram;
	private JMenuBar mb;
	private JMenu file;
	private JMenu help;
	private JMenuItem about;
	private JDialog contactInfo;
	
	public ClientGUI() {
		/* Name the program's frame and set its size */
		mainProgram = new JFrame("SimpleChatClient");
		mainProgram.setSize(500, 500);
		
		// Create a panel that will hold all other elements of the GUI
		JPanel panel = new JPanel();
		panel.setBounds(40, 80, 200, 200);
		panel.setBackground(Color.gray);
		
		// Add a menu bar
		mb = new JMenuBar();
			
		// Create menus that appear in menu bar
		file = new JMenu("File");
		help = new JMenu("Help");
			
		// In Help menu, user can get version information and contact details 
		about = new JMenuItem("About");
		help.add(about);
		
		// Create a dialog box for contact information and version history
		contactInfo = new JDialog(mainProgram);
        contactInfo.setTitle("About SimpleChatGUI Client.");
        contactInfo.setSize(new Dimension(850, 100));
		
		// Add elements to the dialog (description and button)
		JPanel pan=new JPanel();
		pan.setLayout(new FlowLayout());
		pan.add(new JLabel("Simple Chat Client GUI is licensed under MIT license. Furthermore, OCSF component of Simple Chat was made at University of Ottawa"));
		pan.add(new JButton("Close"));
		contactInfo.add(pan);
		
		// Location and visibility
        contactInfo.setLocationRelativeTo(mainProgram);
        contactInfo.setModal(false);
        contactInfo.setVisible(false);
			
		// Add action listeners to the menu items
		about.addActionListener(this);

		// Add menus to the menu bar. Then, add the menu and panel bar to the frame
		mb.add(file);
		mb.add(help);
		mainProgram.setJMenuBar(mb);
		mainProgram.add(panel);
		
		// Operations for closing the client and its display
		mainProgram.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainProgram.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==about) {
			// Display the dialog box
			contactInfo.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		ClientGUI gui = new ClientGUI();
	}
}