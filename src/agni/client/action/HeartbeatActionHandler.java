package agni.client.action;

import agni.client.communication.MessageSender;

public class HeartbeatActionHandler {
    private MessageSender messageSender;

    public HeartbeatActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendHeartbeat(byte status) {
    	
    }

}
