package test.agni.server.receiver;

import java.nio.ByteBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.StatusListener;
import agni.server.receiver.HeartbeatReceiver;

public class HeartBeatReceiverTest {
	final int headerBytes = 5;
	final byte type = 0x01;
	final String testIp = "192.168.1.1";
	final byte testStatus = 0x01;
	int totalMessageLength; 
	ByteBuffer testBuffer = null;

	Mockery context = new Mockery();
	StatusListener mockStatusListener;
	HeartbeatReceiver hbReceiver;
	
	@Before
	public void setUp() throws Exception {
		//prepare the message
		totalMessageLength = (headerBytes + 1);

		
		//populate message buffer
		testBuffer = ByteBuffer.wrap(new byte[100]);
		testBuffer.putInt(totalMessageLength);
		testBuffer.put(type);
		testBuffer.put(testStatus);

		hbReceiver = new HeartbeatReceiver();
		
		mockStatusListener = context.mock(StatusListener.class);
		hbReceiver.register(mockStatusListener);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void correctInputTest() {
		context.checking(new Expectations() {{
			oneOf(mockStatusListener).ReceivedHeartBeat("192.168.1.1", testStatus);
		}});
		hbReceiver.receiveMessage(testIp, testBuffer);
		context.assertIsSatisfied();
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void nullIpTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			oneOf(mockStatusListener).ReceivedHeartBeat(expectedIp, testStatus);
		}});
		hbReceiver.receiveMessage(null, testBuffer);
		context.assertIsSatisfied();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullMessageTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			oneOf(mockStatusListener).ReceivedHeartBeat(expectedIp, testStatus);
		}});
		hbReceiver.receiveMessage(testIp, null);
		context.assertIsSatisfied();
	}
	
}

