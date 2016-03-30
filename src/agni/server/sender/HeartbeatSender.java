package agni.server.sender;

import agni.server.communication.I_MessageSender;

public class HeartbeatSender {
    private I_MessageSender messageSender;

    public HeartbeatSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendHeartbeat(String dest_ip) {

    }
}
