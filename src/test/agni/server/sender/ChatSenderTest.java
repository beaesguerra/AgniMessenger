package test.agni.server.sender;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import agni.server.communication.MessageSender;
import agni.server.sender.ChatSender;
import test.AgniTestUtilities;

public class ChatSenderTest {

    final String TEST_IP = "localhost";
    final String CHAT_BYTE_HEX = "09";

    // ASCII converted to hex at http://www.5asciitohex.com/
    final String TEST_USER = "TestUser";
    final String TEST_SENDER_LENGTH_HEX = "08";
    final String TEST_SENDER_NAME_HEX = "5465737455736572";

    final String TEST_MESSAGE = "This is a test message!";
    final String TEST_MESSAGE_HEX = "5468697320697320612074657374206d657373616765";
    MessageSender messageSender;
    ChatSender chatSender;
    Mockery context;

    public void setup() {
        this.context = new Mockery();
        this.messageSender = context.mock(MessageSender.class);
        this.chatSender = new ChatSender(messageSender);
    }

    @Test
    public void normalMessage() {
        context.checking(new Expectations() {{
            final String LENGTH_HEX = "00000024"; // 36 base 10
            final byte[] expectedMessage = AgniTestUtilities.hexStringToByteArray(LENGTH_HEX +
                                                                                  CHAT_BYTE_HEX +
                                                                                  TEST_SENDER_LENGTH_HEX +
                                                                                  TEST_SENDER_NAME_HEX +
                                                                                  TEST_MESSAGE_HEX);

            try {
                oneOf(messageSender).sendMessage(InetAddress.getByName(TEST_IP), expectedMessage);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }});

        chatSender.sendChat(TEST_IP, TEST_USER, TEST_MESSAGE);
    }

    @Test(expected=NullPointerException.class)
    public void nullIp() {
        chatSender.sendChat(null, TEST_USER, TEST_MESSAGE);
    }

    @Test(expected=NullPointerException.class)
    public void nullUser() {
        chatSender.sendChat(TEST_IP, null, TEST_MESSAGE);
    }

    @Test(expected=NullPointerException.class)
    public void nullMessage() {
        chatSender.sendChat(TEST_IP, TEST_USER, null);
    }
}
