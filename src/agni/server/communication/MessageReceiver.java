package agni.server.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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

/*
 *
 * 
 * 
 */
   public void waitForClients() {
    try {
        //boolean terminated = false;
        while (!terminated) 
        {
            if (selector.select(500) < 0)
            {
                System.out.println("select() failed");
                System.exit(1);
            }
            
            // Get set of ready sockets
            Set readyKeys = selector.selectedKeys();
            Iterator readyItor = readyKeys.iterator();

            // Walk through the ready set
            while (readyItor.hasNext()) 
            {
                // Get key from set
                SelectionKey key = (SelectionKey)readyItor.next();

                // Remove current entry
                readyItor.remove();

                // Accept new connections, if any
                if (key.isAcceptable())
                {
                    
                    SocketChannel cchannel = ((ServerSocketChannel)key.channel()).accept();
                    cchannel.configureBlocking(false);
                    System.out.println("Accept conncection from " + cchannel.socket().toString());
                    
                    // Register the new connection for read operation
                    cchannel.register(selector, SelectionKey.OP_READ);
                } 
                else 
                {
                    SocketChannel cchannel = (SocketChannel)key.channel();
                    if (key.isReadable())
                    {
                        //Socket socket = cchannel.socket();
           
                        // Open input and output streams
                        inBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);
                        cBuffer = CharBuffer.allocate(BUFFERSIZE);
                         
                        // Read from socket
                        bytesRecv = cchannel.read(inBuffer);
                        if (bytesRecv <= 0)
                        {
                            System.out.println("read() error, or connection closed");
                            key.cancel();  // deregister the socket
                            continue;
                        }
                        inBuffer.flip();      // make buffer available
                        decoder.decode(inBuffer, cBuffer, false);
                        cBuffer.flip();
                        line = cBuffer.toString();
                        commandHandler(line,cchannel);
                   }
                }
            } // end of while (readyItor.hasNext()) 
        } // end of while (!terminated)
    }
    catch (IOException e) {
        System.out.println(e);
    }

   }
}

