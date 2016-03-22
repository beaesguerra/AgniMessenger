package agni.server.communication;

public class MessageReceiver implements Runnable {

    private Selector selector = null;
    private ServerSocketChannel channel = null;


    public static void main(String[] args) {

        initializeConnection (args[0]);

        //TODO instantiate receivers
        
    }

/*
*@requires server port number
*@promises selector initialized and ready for connection requests
*@promises socket channel is initialized to be non-blocking and bound to specified port
*/
    private void initializeConnection(String port) {

        channel. ServerSocketChannel.open();
        channel.configureBlocking(false);
        InetSocketAddress isa  new InetSocketAddress (Integer.parseInt(port));
        channel.socket().bind(isa);

        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public getSelector

}

}
