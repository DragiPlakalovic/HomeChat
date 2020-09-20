import ocsf.client.*;

public class HomeChatClient extends AbstractClient {
	// Create a Client instance
	public HomeChatClient(String localhost, int port, Client c) {
		super(localhost, port);
		try {
			openConnection();
		}
		catch (Exception e) {
			c.generateCreateUserErrorMessage(e.getMessage());
		}
	}
	
	// Method for handling messages
	public void handleMessageFromServer(Object msg) {
		return;
	}
}