package agni.server.receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class LoginReceiver implements MessageParser {

    private Vector <LoginListener> loginListeners = null;

    public LoginReceiver() {
        loginListeners = new Vector<LoginListener>();
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
        message.get(parsedMessage, 5, (length));
        return parsedMessage;
    }


    @Override
    public void receiveMessage(SocketChannel channel, ByteBuffer message) {

        byte[] parsedMessage = this.parseMessage(message); 
        notifyloginRequest(channel, message);  
    }

}
