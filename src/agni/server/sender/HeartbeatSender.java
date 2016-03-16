package agni.server.sender;

import agni.server.communication.MessageSender;

public class HeartbeatSender {
    private MessageSender messageSender;

    public HeartbeatSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendHeartbeat(String dest_ip) {

    }
}
