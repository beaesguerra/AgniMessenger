package agni.client.receiver;

import agni.client.view.*;

public abstract class MessageParser {
	private ReceiverListener[] listeners;
	
	public MessageParser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void register(AgniClientView listener) {
		
	}
	
	public void deregister(AgniClientView listener) {
		
	}
	
	public void receiveMessage(String message) {
		
	}
}
