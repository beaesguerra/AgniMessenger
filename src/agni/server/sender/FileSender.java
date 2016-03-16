package agni.server.sender;

import agni.client.communication.MessageSender;

public class FileSender {
    private MessageSender messageSender;

    public FileSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendFile(String dest_ip, String srcName, String message) {

    }
}
