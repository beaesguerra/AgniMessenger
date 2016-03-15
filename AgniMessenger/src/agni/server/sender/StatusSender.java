package agni.server.sender;

import agni.server.communication.MessageSender;

public class StatusSender {
    private MessageSender messageSender;

    public enum Status {
    	OFFLINE, 
    	ONLINE,
    	AWAY
    }

    public StatusSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendStatus(String dest_ip, Status status, String friendName) {

    }
}
