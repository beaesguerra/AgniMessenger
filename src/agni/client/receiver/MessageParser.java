package agni.client.receiver;

import agni.client.view.*;

public abstract class MessageParser {
    private ReceiverListener[] listeners;

    public abstract void register(AgniClientView listener);
    public abstract void deregister(AgniClientView listener);
    public abstract void receiveMessage(byte[] message);
}
