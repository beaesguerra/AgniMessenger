package test.agni.server.sender;

import static org.junit.Assert.*;

import org.jmock.Mockery;
import org.junit.Test;

import agni.server.communication.MessageSender;

public class ChatSenderTest {

    public void setup() {
        Mockery context = new Mockery();
        final MessageSender messageSender = context.mock(MessageSender.class);
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
