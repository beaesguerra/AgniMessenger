package agni.server.sender;

import agni.server.communication.I_MessageSender;
import agni.server.communication.MessageSender;

public class FileSender {
    private I_MessageSender messageSender;

    public FileSender(I_MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendFile(String dest_ip, String srcName, String message) {

    }
}
