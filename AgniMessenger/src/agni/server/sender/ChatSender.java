package agni.server.sender;

import agni.server.communication.MessageSender;

public class ChatSender {
    private MessageSender messageSender;

    public ChatSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

	public void sendChat(String dest_ip, String srcName, String message) {

    }
}
