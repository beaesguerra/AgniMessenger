package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.UserDataGuard;

public class UserDataGuardTest {

    private UserDataGuard userDataGuard;

    @Before
    public void setup() throws SQLException {
        String dbName = "agni_test";
        String dbUserName = "agni_tester";
        String source = "AgniTest.sql";
        String[] commands =  new String[] {"/usr/local/bin/mysql", "--user=" + dbUserName, dbName, "-e", "source " + source};
        try {
            Runtime.getRuntime().exec(commands);
            Thread.sleep(300);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        userDataGuard = new UserDataGuard("agni_test", "agni_tester", "");
    }

    @Test
    public void registerUserTest() {
        userDataGuard.registerUser("SomeTestUser", "asalt", "ahash");
        assertTrue(userDataGuard.salt("SomeTestUser").equals("asalt"));
        assertTrue(userDataGuard.getPasswordHash("SomeTestUser").equals("ahash"));
    }

    @Test
    public void getSaltTest() {
        assertTrue(userDataGuard.salt("TestUser").equals("T35t54Lt"));
    }

    @Test
    public void getPasswordHashTest() {
        assertTrue(userDataGuard.getPasswordHash("TestUser").equals("T35TH45H"));
    }

    @Test
    public void getFriendsTest() {
        String[] expectedFriends = {"1234567890"};
        String[] actualFriends = userDataGuard.getFriends("BillyBob");

        Arrays.sort(expectedFriends);
        Arrays.sort(actualFriends);

        assertArrayEquals(actualFriends, expectedFriends);
    }

    public void createFriendship(String user1, String user2) {
        userDataGuard.createFriendship("EnochTsang", "TestUser");

        String[] expectedFriends = {"TestUser"};
        String[] actualFriends = userDataGuard.getFriends("EnochTsang");

        Arrays.sort(expectedFriends);
        Arrays.sort(actualFriends);

        assertArrayEquals(actualFriends, expectedFriends);
    }

    @Test
    public void userCurrentIpTest() {
        assertEquals(userDataGuard.userCurrentIp("TestUser"), null);
        userDataGuard.loginUser("192.168.0.1", "TestUser");
        assertTrue(userDataGuard.userCurrentIp("TestUser").equals("192.168.0.1"));

        userDataGuard.changeUserCurrentIp("TestUser", null);
    }

    @Test
    public void getUserNameTest() {
    	userDataGuard.loginUser("192.168.0.1", "Testman");
        assertTrue(userDataGuard.getUsername("192.168.0.1").equals("Testman"));
    }

    @Test
    public void loginUserTest() {
        assertFalse(userDataGuard.isOnline("TestUser"));
        userDataGuard.loginUser("192.168.1.1", "TestUser");
        assertTrue(userDataGuard.isOnline("TestUser"));

        assertFalse(userDataGuard.isOnline("EnochTsang"));
        userDataGuard.loginUser("192.168.1.2", "EnochTsang");
        assertTrue(userDataGuard.isOnline("EnochTsang"));
    }

    public void logoutUserTest() {
        assertFalse(userDataGuard.isOnline("TestUser"));
        userDataGuard.loginUser("192.168.1.1", "TestUser");
        userDataGuard.changeUserCurrentIp("TestUser", null);
        assertFalse(userDataGuard.isOnline("TestUser"));
    }

    @Test
    public void userExistsTrueTest(){
    	userDataGuard.loginUser("1", "TestUser");
        assertTrue(userDataGuard.userExists("TestUser"));
    }

    @Test
    public void userExistsFalseTest(){
        assertFalse(userDataGuard.userExists("NonExistentUser"));
    }

    @Test
    public void heartbeatTest(){
        userDataGuard.loginUser("1", "TestUser");
        assertEquals(userDataGuard.getLastHeartbeat("TestUser"), 0);
        userDataGuard.addToLastHeartbeat("TestUser", 69);
        assertEquals(userDataGuard.getLastHeartbeat("TestUser"), 69);
        userDataGuard.addToLastHeartbeat("TestUser", 30);
        assertEquals(userDataGuard.getLastHeartbeat("TestUser"), 99);
        userDataGuard.resetLastHeartbeat("TestUser");
        assertEquals(userDataGuard.getLastHeartbeat("TestUser"), 0);
    }

    @Test
    public void getOnlineUserIpsTest(){
        String[] expectedOnline = {};
        String[] actualOnline = userDataGuard.getOnlineUserIps();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.loginUser("1", "EnochTsang");
        expectedOnline = new String[] {"1"};
        actualOnline = userDataGuard.getOnlineUserIps();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.loginUser("2", "BillyBob");
        expectedOnline = new String[] {"1", "2"};
        actualOnline = userDataGuard.getOnlineUserIps();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.changeUserCurrentIp("EnochTsang", null);
        expectedOnline = new String[] {"2"};
        actualOnline = userDataGuard.getOnlineUserIps();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);
    }

    @Test
    public void getOnlineUsernamesTest(){
        String[] expectedOnline = {};
        String[] actualOnline = userDataGuard.getOnlineUsernames();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.loginUser("1", "EnochTsang");
        expectedOnline = new String[] {"EnochTsang"};
        actualOnline = userDataGuard.getOnlineUsernames();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.loginUser("2", "BillyBob");
        expectedOnline = new String[] {"EnochTsang", "BillyBob"};
        actualOnline = userDataGuard.getOnlineUsernames();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);

        userDataGuard.changeUserCurrentIp("EnochTsang", null);
        expectedOnline = new String[] {"BillyBob"};
        actualOnline = userDataGuard.getOnlineUsernames();
        Arrays.sort(expectedOnline);
        Arrays.sort(actualOnline);
        assertArrayEquals(actualOnline, expectedOnline);
    }

    @Test
    public void getIpTest(){
        userDataGuard.loginUser("192.1", "BillyBob");
        assertTrue(userDataGuard.getIp("BillyBob").equals("192.1"));        
    }
}
