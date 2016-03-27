package test.agni.server.receiver;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.InfoListener;
import agni.server.receiver.InfoRequestReceiver;

public class InfoRequestReceiverTest {
	final String testIp = "192.168.1.1";
	final int headerBytes = 5;
	final byte type = 3;
	byte[] testArray = null;	
	int totalMessageLength; 
	ByteBuffer testBuffer = null;

	final Mockery context = new Mockery();
	InfoListener mockInfoListener;
	InfoRequestReceiver infoReceiver;
	
	@Before
	public void setUp() throws Exception {
		
		//prepare the message
		byte messageType = 0x03;
		totalMessageLength = (headerBytes + 1);
		
		//populate message buffer
		testBuffer.putInt(totalMessageLength);
		testBuffer.putInt(type);
		testBuffer.put(messageType);
		
		mockInfoListener = context.mock(InfoListener.class);
		infoReceiver = new InfoRequestReceiver();
		infoReceiver.register(mockInfoListener);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void correctInputTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			final byte expectedType = 0x03;
			oneOf(mockInfoListener).infoRequest(expectedIp, expectedType);
		}});
		infoReceiver.receiveMessage(testIp, testBuffer);
		context.assertIsSatisfied();
	}
	
	
	@Test
	public void nullChannelTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			final byte expectedType = 0x02;
			oneOf(mockInfoListener).infoRequest(expectedIp, expectedType);
		}});
		infoReceiver.receiveMessage(null, testBuffer);
		context.assertIsSatisfied();
	}
	
	@Test
	public void nullMessageTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			final byte expectedType = 0x02;
			oneOf(mockInfoListener).infoRequest(expectedIp, expectedType);
		}});
		infoReceiver.receiveMessage(testIp, null);
		context.assertIsSatisfied();
	}
}
	