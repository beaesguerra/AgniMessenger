package test.agni.server.receiver;


import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReceiverTest {
	final int port = 99;
	final int headerBytes = 5;
	final byte type = 1;
	final String testString = "Hello World!";
	byte[] testArray = null;	
	int totalMessageLength; 
	ByteBuffer testBuffer = null;
	
	SocketAddress address = null; 
	SocketChannel testChannel;
	
	String expectedIp = "192.168.1.1";
	byte[] expectedTestArray = null;


	@Before
	public void setUp() throws Exception {
		//set up channel
		address = new InetSocketAddress("192.168.1.1", port);
		testChannel = SocketChannel.open(address);
		
		//prepare the message
		testArray = testString.getBytes("us-ascii");
		totalMessageLength = (headerBytes + testArray.length);
		
		//populate message buffer
		testBuffer.putInt(totalMessageLength);
		testBuffer.putInt(type);
		testBuffer.put(testArray);


	}

	@After
	public void tearDown() throws Exception {
		testChannel.close();
		testBuffer.clear();
	}

	@Test
	public void correctInputTest() {
		
	
	}
	
	@Test
	public void missingIpChannelTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void nullChannelTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void nullMessageTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void emptyMessageTest() {
		fail("Not yet implemented");
	}
	

}
