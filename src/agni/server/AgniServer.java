package agni.server;

public class AgniServer {

    private Selector selector = null;
    private ServerSocketChannel channel = null;



    public static void main(String[] args) {

        initializeConnection (args[0]);
        //TODO Prepare socket channels
        //TODO listen for connections
        //TODO accept connections 

        //TODO instantiate receivers
        //TODO instantiate Senders
        //TODO instantiate Managers

        //TODO direct incomming messages to appropriate receiver
        //TODO send messages from senders
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

}
