package test.agni.server.sender;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import agni.server.communication.I_MessageSender;
import agni.server.communication.MessageSender;
import agni.server.sender.HeartbeatSender;
import test.AgniTestUtilities;

public class HeartbeatSenderTest {

    final String TEST_IP = "localhost";
    final String HEARTBEAT_BYTE_HEX = "07";
    final String LENGTH_HEX = "00000005";

    I_MessageSender messageSender;
    HeartbeatSender heartbeatSender;
    Mockery context;

    @Before
    public void setup() {
        this.context = new Mockery();
        this.messageSender = context.mock(I_MessageSender.class);
        this.heartbeatSender = new HeartbeatSender(messageSender);
    }

    @Test
    public void normalHeartbeat() {
        context.checking(new Expectations() {{
            final byte[] expectedMessage = AgniTestUtilities.hexStringToByteArray(LENGTH_HEX + HEARTBEAT_BYTE_HEX);
            try {
                oneOf(messageSender).sendMessage(InetAddress.getByName(TEST_IP), expectedMessage);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }});

        heartbeatSender.sendHeartbeat(TEST_IP);
        context.assertIsSatisfied();
    }

    @Test(expected=NullPointerException.class)
    public void nullIp() {
        heartbeatSender.sendHeartbeat(null);
    }
}
