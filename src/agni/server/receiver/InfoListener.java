package agni.server.receiver;

public interface InfoListener {

    /*
    * Receives user requests from the InfoRequestReceiver
    * ip will have been converted from inet to String
    */
    public void infoRequest (String ip, byte type);
}
