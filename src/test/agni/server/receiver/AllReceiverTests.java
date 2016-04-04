package test.agni.server.receiver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ChatReceiverTest.class, FileReceiverTest.class,
		InfoRequestReceiverTest.class,
		LoginReceiverTest.class, UserReceiverTest.class })
public class AllReceiverTests {

}
