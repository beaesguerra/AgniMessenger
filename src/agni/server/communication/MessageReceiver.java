package agni.server.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
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
    
/*
 * 
 * 
 */
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
 * 
 * 
 */
    private void selectReceiver(String ip, ByteBuffer buffer){
    	byte type = buffer.get(4);
    	
    	switch (type) {
    	
    	case 1: type = 0x01;//Heartbeat
    	heartBeatReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	case 2: type = 0x02;//Login
    	loginReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	case 3: type = 0x03;//information Request
    	infoRequestReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	case 4: type = 0x04;//User Action
    	userReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	case 5: type = 0x05;//chat
    	chatReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	case 6: type = 0x06;//File
    	fileReceiver.receiveMessage(ip, buffer);
    	break;
    	
    	}
    	
    }
    
/*
 *
 * 
 * 
 */
    public void waitForClients() {
	   boolean terminated = false;
	   ByteBuffer inBuffer = null;
	   ByteBuffer outBuffer = null;
       CharsetDecoder decoder = charset.newDecoder();  
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
				Set readyKeys = selector.selectedKeys();
				Iterator readyItor = readyKeys.iterator();

				// Walk through the ready set
				while (readyItor.hasNext()) {
					// Get key from set
					SelectionKey key = (SelectionKey) readyItor.next();

					// Remove current entry
					readyItor.remove();

					// Accept new connections, if any
					if (key.isAcceptable()) {

						SocketChannel cchannel = ((ServerSocketChannel) key
								.channel()).accept();
						cchannel.configureBlocking(false);
						System.out.println("Accept conncection from "
								+ cchannel.socket().toString());

						// Register the new connection for read operation
						cchannel.register(selector, SelectionKey.OP_READ);
						saveIpChannelPair(cchannel);
					} else {
						SocketChannel cchannel = (SocketChannel) key.channel();
						if (key.isReadable()) {
							// Socket socket = cchannel.socket();

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
							outBuffer.flip(); //TODO not sure if this is right
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
