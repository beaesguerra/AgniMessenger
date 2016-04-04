package agni.server.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class MessageSender implements I_MessageSender {
    private ChannelList channelList;

    public MessageSender(ChannelList channels) {
        channelList = channels;
    }

    public void sendMessage(String ipAddress, byte[] message) {
        System.out.print("SENDING BYTE ARRAY ");
        for (int i = 0; i < message.length; i++) {
            System.out.print(message[i] + ",");
        }
        System.out.println(" DONE SENDING BYTE ARRAY");
        // System.out.println(new String(message, StandardCharsets.US_ASCII));
        SocketChannel currentChannel = channelList.getChannel(ipAddress);
        ByteBuffer outBuffer = ByteBuffer.wrap(addNewline(message));
        try {
            currentChannel.write(outBuffer);
        } catch (IOException e) {
            System.out.println("IOException caught while writing to Client MessageSender outBuffer ");
            e.printStackTrace();
        }
    }
    
    byte[] addNewline(byte[] message) {
        byte[] result = Arrays.copyOf(message, message.length + 1);
        result[message.length] = (byte)0x0A;
        return result;
    }
}
