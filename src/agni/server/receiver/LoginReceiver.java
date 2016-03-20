package agni.server.receiver;

public class LoginReceiver implements MessageParser {

    private Vector <LoginListener> loginListeners = null;

    public LoginReceiver() {
        loginListeners = new Vector();
    }

    private void notifyLoginRequest(String ip, String user, String password) {

        for( LoginListener  lListener: loginListeners )
            lListener.loginRequest (ip, user, password);
    }

    public void register(LoginListener lListener) {

        loginListeners.add(lListener);
    }

    private byte[] parseMessage(ByteBuffer message) {
        
        int length = message.remaining();
        byte[] parsedMessage = new byte[length];
        message.get(parsedMessage, 5, (length);
        return parsedMessage;
    }


    @Overide
    public void receiveMessage(InetAddress ip, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyloginRequest(ip.toString(), message);  
    }

}
