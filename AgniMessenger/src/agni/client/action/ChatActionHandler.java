package agni.client.action;

import agni.client.communication.MessageSender;

public class ChatActionHandler {
    private MessageSender messageSender;

	public ChatActionHandler(MessageSender messageSender) {
    	this.messageSender = messageSender;
	}	

	public void requestChatAction(String message){
		
	}
}
