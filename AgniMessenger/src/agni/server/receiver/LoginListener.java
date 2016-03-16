package agni.server.receiver;

public interface LoginListener {

    /*
    * Receives user requests from the Loginreceiver
    * ip will have been converted from inet to String
    */
    public void loginRequest (String ip, String user, String password);
}
