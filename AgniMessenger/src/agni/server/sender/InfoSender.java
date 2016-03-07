package agni.server.sender;

import agni.client.communication.MessageSender;

public class InfoSender {
	private MessageSender messageSender;

	public InfoSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	public void sendInfo(String dest_ip, String message) {
		
	}
}
