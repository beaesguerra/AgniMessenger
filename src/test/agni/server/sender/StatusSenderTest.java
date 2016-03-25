package test.agni.server.sender;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import agni.server.communication.MessageSender;
import agni.server.sender.StatusSender;
import test.AgniTestUtilities;

public class StatusSenderTest {

    final String TEST_IP = "localhost";
    final String STATUS_BYTE_HEX = "0b";
    
    final StatusSender.Status TEST_STATUS_TYPE = StatusSender.Status.AWAY;
    final String TEST_STATUS_TYPE_HEX = "02";
    
    // ASCII converted to hex at http://www.asciitohex.com/
    final String TEST_FRIEND = "friend";
    final String TEST_FRIEND_HEX = "667269656e64";

    MessageSender messageSender;
    StatusSender statusSender;
    Mockery context;

    public void setup() {
        this.context = new Mockery();
        this.messageSender = context.mock(MessageSender.class);
        this.statusSender = new StatusSender(messageSender);
    }

    @Test
    public void testMessageTypeByte() {
	    final String LENGTH_HEX = "0000000c";
        context.checking(new Expectations() {{
            final byte[] expectedMessage = AgniTestUtilities.hexStringToByteArray(LENGTH_HEX +
            																	  STATUS_BYTE_HEX +
            																	  TEST_FRIEND_HEX);
            oneOf(messageSender).sendMessage(TEST_IP, expectedMessage);
        }});

        statusSender.sendStatus(TEST_IP, TEST_STATUS_TYPE, TEST_FRIEND);
    }   
    
    @Test(expected=NullPointerException.class) 
    public void nullIp() {
        statusSender.sendStatus(null, TEST_STATUS_TYPE, TEST_FRIEND);
    }

    @Test(expected=NullPointerException.class) 
    public void nullStatusType() {
        statusSender.sendStatus(TEST_IP, null, TEST_FRIEND);
    }

    @Test(expected=NullPointerException.class) 
    public void nullFriend() {
        statusSender.sendStatus(TEST_IP, TEST_STATUS_TYPE, null);
    }
}
