package agni.server.receiver;

public interface ChatListener {

    /*
    * Receives user requests from the ChatReceiver
    * ip will have been converted from inet to String
    */
    public void chatRequest (String ip, byte[] message);
}
