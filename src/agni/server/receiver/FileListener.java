package agni.server.receiver;

public interface FileListener {

    /*
    * Receives user requests from the FileReceiver
    * ip will have been converted from inet to String
    */
    public void chatRequest (String ip, byte type);
}
