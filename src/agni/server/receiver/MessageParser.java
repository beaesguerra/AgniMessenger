package agni.server.receiver;

public interface MessageParser {    
    public void receiveMessage(String ip, byte[] message);
}
