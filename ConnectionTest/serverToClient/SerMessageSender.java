import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class SerMessageSender implements I_MessageSender {
    private ChannelList channelList;
  
    public SerMessageSender(ChannelList channels) {
      channelList = channels;
    }

    public void sendMessage(String ipAddress, byte[] message) {
       SocketChannel currentChannel = channelList.getChannel(ipAddress);
       ByteBuffer outBuffer = ByteBuffer.wrap(message);
       try {
        currentChannel.write(outBuffer);
       } catch (IOException e) {
        System.out.println("IOException caught while writing to Client SerMessageSender outBuffer ");
        e.printStackTrace();
       }
    }
}
