package agni.client.action;

import agni.client.communication.MessageSender;

public class InfoRequestActionHandler {
    private MessageSender messageSender;

    public InfoRequestActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
}
