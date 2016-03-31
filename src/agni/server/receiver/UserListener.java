package agni.server.receiver;

public interface UserListener {

    /*
    * Receives user requests from the UserReceiver
    * ip will have been converted from inet to String
    */
    public void userRequest (String ip, byte type, String message);
}
