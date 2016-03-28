package agni.server.sender;

import agni.server.communication.I_MessageSender;

public class StatusSender {
    private I_MessageSender messageSender;

    public enum Status {
    	OFFLINE,
    	ONLINE,
    	AWAY
    }

    public StatusSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendStatus(String dest_ip, Status status, String friendName) {

    }
}
