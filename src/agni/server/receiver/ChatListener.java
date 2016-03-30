package agni.server.receiver;

import java.nio.ByteBuffer;
import java.util.Arrays;

public interface ChatListener {

    public void chatRequest (String ip, byte[] message);
}

/*
private byte[] parseMessage(ByteBuffer message) {
    int length = message.remaining();
    byte[] byteArray = new byte[length];
    message.get(byteArray);
    byte[] parsedMessage = Arrays.copyOfRange(byteArray,5,length);
    return parsedMessage;
*/