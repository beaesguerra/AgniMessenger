package agni.server.sender;

import agni.server.communication.I_MessageSender;
import agni.server.communication.MessageSender;

public class ChatSender {
    private I_MessageSender messageSender;

    public ChatSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendChat(String dest_ip, String srcName, String message) {

    }
}
