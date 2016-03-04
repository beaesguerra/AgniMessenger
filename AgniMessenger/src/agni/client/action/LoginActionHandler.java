package agni.client.action;

import agni.client.communication.MessageSender;

public class LoginActionHandler {
	private MessageSender messageSender;

	public LoginActionHandler(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
}
