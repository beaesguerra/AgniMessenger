package agni.server.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


public class MessageSender implements I_MessageSender {
    private ChannelList channelList;
  
    public MessageSender(ChannelList channels) {
      channelList = channels;
    }

    public void sendMessage(String ipAddress, byte[] message) {
    	System.out.println(new String(message, StandardCharsets.US_ASCII));
       SocketChannel currentChannel = channelList.getChannel(ipAddress);
       ByteBuffer outBuffer = ByteBuffer.wrap(message);
       try {
        currentChannel.write(outBuffer);
       } catch (IOException e) {
        System.out.println("IOException caught while writing to Client MessageSender outBuffer ");
        e.printStackTrace();
       }
    }
}
