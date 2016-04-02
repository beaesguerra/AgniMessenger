package test.agni.server.receiver;

import java.nio.ByteBuffer;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.ChatReceiver;
import agni.server.receiver.FileListener;
import agni.server.receiver.FileReceiver;

public class FileReceiverTest {
    final int headerBytes = 5;
    final byte type = 0x06;
    final byte EOF = 0x01;
    final byte fileNameLength = 0x04;
    final String testIp = "192.168.1.1";
    final String testFileName = "test";
    final String testFile = "Hello World!";
    byte[] testFileArray = null;
    byte[] testFileNameArray = null;
    byte[] bufferArray= null;

    int totalMessageLength; 
    ByteBuffer testBuffer = null;

    Mockery context = new Mockery();
    FileListener mochFileListener;
    FileReceiver fileReceiver;
    
    @Before
    public void setUp() throws Exception {
        //prepare the message
        testFileNameArray = testFileName.getBytes("us-ascii");
        testFileArray = testFile.getBytes("us-ascii");
        totalMessageLength = (headerBytes + testFileArray.length + testFileNameArray.length + 2);

        
        //populate message buffer
        testBuffer = ByteBuffer.wrap(new byte[100]);
        testBuffer.putInt(totalMessageLength);
        testBuffer.put(type);
        testBuffer.put(EOF);
        testBuffer.put(fileNameLength);
        testBuffer.put(testFileNameArray);
        testBuffer.put(testFileArray);
        
        testBuffer.flip();
        int length =  testBuffer.remaining();
        bufferArray = new byte[length];
        testBuffer.get(bufferArray);

        fileReceiver = new FileReceiver();
        
        mochFileListener = context.mock(FileListener.class);
        fileReceiver.register(mochFileListener);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void correctInputTest() {
        context.checking(new Expectations() {{
            oneOf(mochFileListener).fileRequest("192.168.1.1", EOF, testFileName, testFileArray);
        }});
        fileReceiver.receiveMessage(testIp, bufferArray);
        context.assertIsSatisfied();
    }
    
    
    @Test(expected = NullPointerException.class)
    public void nullIpTest() {
        context.checking(new Expectations() {{
            oneOf(mochFileListener).fileRequest("192.168.1.1", EOF, testFileName, testFileArray);
        }});
        fileReceiver.receiveMessage(null, bufferArray);
        context.assertIsSatisfied();
    }
    
    @Test(expected = NullPointerException.class)
    public void nullMessageTest() {
        context.checking(new Expectations() {{
            oneOf(mochFileListener).fileRequest("192.168.1.1", EOF, testFileName, testFileArray);
        }});
        fileReceiver.receiveMessage(testIp, null);
        context.assertIsSatisfied();
    }
    
}

