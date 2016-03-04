package agni.client.action;

import agni.client.communication.MessageSender;

public class UserActionHandler {
    private MessageSender messageSender;

    public UserActionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
    
}
