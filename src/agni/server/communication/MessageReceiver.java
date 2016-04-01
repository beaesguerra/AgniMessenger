package agni.server.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import agni.server.receiver.ChatReceiver;
import agni.server.receiver.FileReceiver;
import agni.server.receiver.HeartbeatReceiver;
import agni.server.receiver.InfoRequestReceiver;
import agni.server.receiver.LoginReceiver;
import agni.server.receiver.UserReceiver;

public class MessageReceiver {
    private Selector selector = null;
    private ServerSocketChannel channel = null;
    private ChannelList channelList = null;
    
    ChatReceiver chatReceiver = null;
    FileReceiver fileReceiver = null;
    HeartbeatReceiver heartBeatReceiver = null;
    InfoRequestReceiver infoRequestReceiver = null;
    LoginReceiver loginReceiver = null;
    UserReceiver userReceiver = null;

    
    public MessageReceiver(ChannelList channels,
            LoginReceiver loginReceiver,
            UserReceiver userReceiver,
            ChatReceiver chatReceiver,
            FileReceiver fileReceiver,
            InfoRequestReceiver infoRequestReceiver, 
            HeartbeatReceiver heartbeatReceiver) {
        
        this.loginReceiver = loginReceiver;
        this.userReceiver = userReceiver;
        this.chatReceiver = chatReceiver;
        this.fileReceiver = fileReceiver;
        this.infoRequestReceiver = infoRequestReceiver; 
        this.heartBeatReceiver = heartbeatReceiver;
        this.channelList = channels;
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
    private void selectReceiver(String ip, ByteBuffer buffer){
        byte messageType = buffer.get(4);
        if (messageType == MessageTypes.HEARTBEAT.bytes()) {
            // Pass to heartbeatReceiver
            heartBeatReceiver.receiveMessage(ip, buffer);
        } else if (messageType == MessageTypes.INFO.bytes()) {
            // Pass to informationReceiver
            infoRequestReceiver.receiveMessage(ip, buffer);
        } else if (messageType == MessageTypes.CHAT.bytes()) {
            // Pass to chatReceiver
            chatReceiver.receiveMessage(ip, buffer);
        } else if (messageType == MessageTypes.FILE.bytes()) {
            // Pass to fileReceiver
            fileReceiver.receiveMessage(ip, buffer);
        } else if (messageType == MessageTypes.STATUS.bytes()) {
            // Pass to statusReceiver
            userReceiver.receiveMessage(ip, buffer);
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
        SocketChannel cchannel = null;
        try {
            cchannel = ((ServerSocketChannel) key
                    .channel()).accept();
            cchannel.configureBlocking(false);
            System.out.println("Accept conncection from "
                    + cchannel.socket().toString());
            cchannel.register(selector, SelectionKey.OP_READ);  
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        saveIpChannelPair(cchannel);
    }
    
/*
 *Listen for client connections and communications 
 *Uses a SocketChannel to queue sockets, service ready sockets sequentially
 * @requires the SocketChannel to be initialized
 * @promises to service ready channels by calling selectReceiver or registerNewClient
 */
    public void waitForClients() {
       boolean terminated = false;
       ByteBuffer inBuffer = null;
       ByteBuffer outBuffer = null;
       int BUFFERSIZE = 32000;
       int bytesRecv = 0;
       
       try {
        //boolean terminated = false;
        while (!terminated) 
        {
                if (selector.select(500) < 0) {
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
                        SocketChannel cchannel = (SocketChannel) key.channel();
                        if (key.isReadable()) {
                            // Open input and output streams
                            inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
                            outBuffer = ByteBuffer.allocate(BUFFERSIZE);

                            // Read from socket
                            bytesRecv = cchannel.read(inBuffer);
                            if (bytesRecv <= 0) {
                                System.out
                                        .println("read() error, or connection closed");
                                key.cancel(); // deregister the socket
                                channelList.removeIP(cchannel.getRemoteAddress().toString());
                                continue;
                            }
                            inBuffer.flip(); // make buffer available
                            outBuffer.put(inBuffer);
                            inBuffer.clear();
                            //outBuffer.flip(); //TODO if it doesn't work try this
                            selectReceiver(cchannel.getRemoteAddress().toString(), outBuffer);
                                                    }
                    }
                } // end of while (readyItor.hasNext())
            } // end of while (!terminated)
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
