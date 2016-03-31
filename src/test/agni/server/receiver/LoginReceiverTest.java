package test.agni.server.receiver;

import java.nio.ByteBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.LoginListener;
import agni.server.receiver.LoginReceiver;


public class LoginReceiverTest {
    final int headerBytes = 5;
    final byte usernameLength = 0x07;
    final byte type = 0x02;
    final String testIp = "192.168.1.1";
    final String testString = "JinglesPassw0rd!";
    byte[] testArray = null;    
    int totalMessageLength; 
    ByteBuffer testBuffer = null;

    Mockery context = new Mockery();
    LoginListener mockLoginListener;
    LoginReceiver loginReceiver;
    
    @Before
    public void setUp() throws Exception {
        //prepare the message
        testArray = testString.getBytes("us-ascii");
        totalMessageLength = (headerBytes + testArray.length);

        
        //populate message buffer
        testBuffer = ByteBuffer.wrap(new byte[100]);
        testBuffer.putInt(totalMessageLength);
        testBuffer.put(type);
        testBuffer.put(usernameLength);
        testBuffer.put(testArray);

        loginReceiver = new LoginReceiver();
        
        mockLoginListener = context.mock(LoginListener.class);
        loginReceiver.register(mockLoginListener);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void correctInputTest() {
        context.checking(new Expectations() {{
            oneOf(mockLoginListener).loginRequest("192.168.1.1", "Jingles", "Passw0rd!");
        }});
        loginReceiver.receiveMessage(testIp, testBuffer);
        context.assertIsSatisfied();
    }
    
    
    @Test(expected = NullPointerException.class)
    public void nullIpTest() {
        context.checking(new Expectations() {{
            oneOf(mockLoginListener).loginRequest("192.168.1.1", "Jingles", "Passw0rd!");
        }});
        loginReceiver.receiveMessage(null, testBuffer);
        context.assertIsSatisfied();
    }
    
    @Test(expected = NullPointerException.class)
    public void nullMessageTest() {
        context.checking(new Expectations() {{
            oneOf(mockLoginListener).loginRequest("192.168.1.1", "Jingles", "Passw0rd!");
        }});
        loginReceiver.receiveMessage(testIp, null);
        context.assertIsSatisfied();
    }
    
}

