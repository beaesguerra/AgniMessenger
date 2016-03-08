package agni.server.sender;

import agni.client.communication.MessageSender;

public class StatusSender {
	private MessageSender messageSender;

	public StatusSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	public void sendStatus(String dest_ip, String friendName, String message) {
		
	}
}
