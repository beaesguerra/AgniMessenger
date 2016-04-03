package agni.server.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class MessageSender implements I_MessageSender {
    private ChannelList channelList;
  
    public MessageSender(ChannelList channels) {
      channelList = channels;
    }

    public void sendMessage(String ipAddress, byte[] message) {
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
