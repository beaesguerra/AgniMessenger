package agni.server.sender;

import agni.server.communication.I_MessageSender;
import agni.server.communication.MessageSender;

public class InfoSender {
    private I_MessageSender messageSender;

    public InfoSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendInfo(String dest_ip, String message) {

    }
}
