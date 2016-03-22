package agni.client.receiver;

import agni.server.sender.StatusSender.Status;

public interface ReceiverListener {

    public void heartbeatReaction();
    public void infoReaction(String message);
    public void statusReaction(String user, Status status);
    public void chatReaction(String sender, String message);
    public boolean fileReaction(String filename, String fromUser, int size);

}
