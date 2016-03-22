package agni.server.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class MessageReceiver {

    private Selector selector = null;
    private ServerSocketChannel channel = null;

/*
*@requires server port number
*@promises selector initialized and ready for connection requests
*@promises socket channel is initialized to be non-blocking and bound to specified port
*/
    public void initializeConnection(String port) {

        try {
			ServerSocketChannel.open();
	        channel.configureBlocking(false);
	        InetSocketAddress isa = new InetSocketAddress (Integer.parseInt(port));
	        channel.socket().bind(isa);

	        selector = Selector.open();
	        channel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }


}

