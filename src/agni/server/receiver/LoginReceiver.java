package agni.server.receiver;

public class LoginReceiver implements MessageParser {

    private Vector <LoginListener> loginListeners = null;

    public LoginReceiver() {
        loginListeners = new Vector();
    }

    private void notifyLoginRequest(SocketChannel channel, String user, String password) {

        for( LoginListener  lListener: loginListeners )
            lListener.loginRequest (channel, user, password);
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
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyloginRequest(channel, message);  
    }

}
