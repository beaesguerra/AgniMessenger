package test.agni.server.receiver;

import java.nio.ByteBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.UserListener;
import agni.server.receiver.UserReceiver;

public class UserReceiverTest {

    final int headerBytes = 5;
    final byte type = 0x01;
    final String testIp = "192.168.1.1";
    final byte testAction = 0x01;
    final String testMessage = "Hello World!";
    byte[] testArray = null;
    int totalMessageLength; 
    ByteBuffer testBuffer = null;

    Mockery context = new Mockery();
    UserListener mockUserListener;
    UserReceiver userReceiver;
    
    @Before
    public void setUp() throws Exception {
        //prepare the message
        testArray = testMessage.getBytes("us-ascii");
        totalMessageLength = (headerBytes + 1 + testArray.length);

        
        //populate message buffer
        testBuffer = ByteBuffer.wrap(new byte[100]);
        testBuffer.putInt(totalMessageLength);
        testBuffer.put(type);
        testBuffer.put(testAction);
        testBuffer.put(testArray);

        userReceiver = new UserReceiver();
        
        mockUserListener = context.mock(UserListener.class);
        userReceiver.register(mockUserListener);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void correctInputTest() {
        context.checking(new Expectations() {{
            oneOf(mockUserListener).infoRequest("192.168.1.1", testAction, testMessage);
        }});
        userReceiver.receiveMessage(testIp, testBuffer);
        context.assertIsSatisfied();
    }
    
    
    @Test(expected = NullPointerException.class)
    public void nullIpTest() {
        context.checking(new Expectations() {{
            oneOf(mockUserListener).infoRequest("192.168.1.1", testAction, testMessage);
        }});
        userReceiver.receiveMessage(null, testBuffer);
        context.assertIsSatisfied();
    }
    
    @Test(expected = NullPointerException.class)
    public void nullMessageTest() {
        context.checking(new Expectations() {{
            oneOf(mockUserListener).infoRequest("192.168.1.1", testAction, testMessage);
        }});
        userReceiver.receiveMessage(testIp, null);
        context.assertIsSatisfied();
    }
    
}
