import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class SerMessageReceiver {
    private Selector selector = null;
    private ServerSocketChannel channel = null;
    private ChannelList channelList = null;

    SerHeartbeatReceiver heartBeatReceiver;

    public SerMessageReceiver(ChannelList channels,
                              SerHeartbeatReceiver heartbeatReceiver) {

        this.heartBeatReceiver = heartbeatReceiver;
        this.channelList = channels;
        try {
            channel = ServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum MessageTypes {
        HEARTBEAT((byte)0x01),
        LOGIN((byte)0x02),
        INFO((byte)0x03),
        STATUS((byte)0x04),
        CHAT((byte)0x05),
        FILE((byte)0x06);

        private final byte bytes;
        private MessageTypes(byte bytes) {
            this.bytes = bytes;
        }
        final public byte bytes() {
            return bytes;
        }
    }

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

    private void saveIpChannelPair(SocketChannel channel) {
        String ip = null;
        try {
            ip = channel.getRemoteAddress().toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        channelList.addPair(ip, channel);
    }

    /*
     * @requires ip of current channel
     * @requires message buffer
     * @promises call appropriate 'receiverMessage(ip, buffer)' method
     */
    private void selectReceiver(String ip, ByteBuffer buffer) {
        buffer.flip();
        int length = buffer.remaining();
        byte[] byteArray = new byte[length];
        buffer.get(byteArray);
        byte messageType = byteArray[4];
        if (messageType == MessageTypes.HEARTBEAT.bytes()) {
            // Pass to heartbeatReceiver
            heartBeatReceiver.receiveMessage(ip, byteArray);
        } else if (messageType == MessageTypes.INFO.bytes()) {
            // Pass to informationReceiver
            System.out.println("SHIT");
        } else if (messageType == MessageTypes.CHAT.bytes()) {
            // Pass to chatReceiver
            System.out.println("SHIT");
        } else if (messageType == MessageTypes.FILE.bytes()) {
            // Pass to fileReceiver
            System.out.println("SHIT");
        } else if (messageType == MessageTypes.STATUS.bytes()) {
            // Pass to statusReceiver
            System.out.println("SHIT");
        } else {
            assert(false);
        }
    }

    /*
     *accept new client connection request
     * @requires SelectionKey key
     * @promises register new client and add new ip/channel pair to list
     */
    public void registerNewClient(SelectionKey key) {
        SocketChannel currentChannel = null;
        try {
            currentChannel = ((ServerSocketChannel) key.channel()).accept();
            currentChannel.configureBlocking(false);
            System.out.println("Accept connection from " + currentChannel.socket().toString());
            currentChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        saveIpChannelPair(currentChannel);
    }

    /*
     *Listen for client connections and communications
     *Uses a SocketChannel to queue sockets, service ready sockets sequentially
     * @requires the SocketChannel to be initialized
     * @promises to service ready channels by calling selectReceiver or registerNewClient
     */
    public void waitForClients() {
        final int errorCheck = 500;
        boolean terminated = false;
        ByteBuffer inBuffer = null;
        ByteBuffer outBuffer = null;
        int BUFFERSIZE = 32000;
        int bytesRecv = 0;

        try {
            //boolean terminated = false;
            while (!terminated) {
                if (selector.select(errorCheck) < 0) {
                    System.out.println("select() failed");
                    System.exit(1);
                }

                // Get set of ready sockets
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> readyItor = readyKeys.iterator();

                // Walk through the ready set
                while (readyItor.hasNext()) {
                    // Get key from set
                    SelectionKey key = (SelectionKey) readyItor.next();
                    // Remove current entry
                    readyItor.remove();

                    // Accept new connections, if any
                    if (key.isAcceptable()) {
                        registerNewClient(key);
                    } else {
                        SocketChannel currentChannel = (SocketChannel) key.channel();
                        if (key.isReadable()) {
                            // Open input and output streams
                            inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
                            outBuffer = ByteBuffer.allocate(BUFFERSIZE);

                            // Read from socket
                            bytesRecv = currentChannel.read(inBuffer);
                            if (bytesRecv <= 0) {
                                System.out.println("read() error, or connection closed");
                                key.cancel(); // deregister the socket
                                channelList.removeIP(currentChannel.getRemoteAddress().toString());
                            }
                            inBuffer.flip(); // make buffer available
                            outBuffer.put(inBuffer);
                            inBuffer.clear();
                            selectReceiver(currentChannel.getRemoteAddress().toString(), outBuffer);
                        }
                    }
                } // end of while (readyItor.hasNext())
            } // end of while (!terminated)
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        ChannelList list = new ChannelList();
        SerHeartbeatReceiver heartbeatReceiver = new SerHeartbeatReceiver();
        SerMessageReceiver recv = new SerMessageReceiver(list, heartbeatReceiver);
        recv.initializeConnection(args[0]);
        recv.waitForClients();
    }
}
