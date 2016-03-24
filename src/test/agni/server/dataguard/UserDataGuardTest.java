package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.UserDataGuard;

public class UserDataGuardTest {

    private UserDataGuard userDataGuard;

    @Before
    public void setup() {
        String dbName = "agni_test";
        String dbUserName = "agni_tester";
        String source = "AgniTest.sql";
        String[] commands =  new String[]{"mysql", "--user=" + dbUserName, dbName,"-e", "source " + source};
        try {
			Runtime.getRuntime().exec(commands);
		} catch (IOException e) {
			e.printStackTrace();
		}
        userDataGuard = new UserDataGuard("agni_test", "agni_tester", "");
    }

    @Test
    public void getSaltTest() {
        assertTrue(userDataGuard.salt("TestUser").equals("T35t54Lt"));
    }

    @Test
    public void getPasswordHashTest() {
        assertTrue(userDataGuard.passwordHash("TestUser").equals("T35TH45H"));
    }

    @Test
    public void addUserTest() {
        userDataGuard.addUser("extraUser", "verysalty", "verypasswordyhash");
        assertTrue(userDataGuard.salt("extraUser").equals("verysalty"));
        assertTrue(userDataGuard.passwordHash("extraUser").equals("verypasswordyhash"));
    }

    @Test
    public void getFriendsTest() {
        String[] expectedFriends = {"BillyBob", "EnochTsang"};
        String[] actualFriends = userDataGuard.friends("TestUser");

        Arrays.sort(expectedFriends);
        Arrays.sort(actualFriends);

        assertArrayEquals(actualFriends, expectedFriends);
    }

    @Test
    public void createFriendshipTest() {
        userDataGuard.addUser("extraUser", "verysalty", "verypasswordyhash");
        userDataGuard.createFriendship("TestUser", "nofriendsUser");
        String[] expectedFriends = {"BillyBob", "EnochTsang", "nofriendsUser"};
        String[] actualFriends = userDataGuard.friends("TestUser");

        Arrays.sort(expectedFriends);
        Arrays.sort(actualFriends);

        assertArrayEquals(actualFriends, expectedFriends);
    }

    @Test
    public void userCurrentIp() {
        assertEquals(userDataGuard.userCurrentIp("TestUser"), null);
        userDataGuard.changeUserCurrentIp("TestUser", "192.168.0.1");
        assertTrue(userDataGuard.userCurrentIp("TestUser").equals("192.168.0.1"));
    }

    @Test
    public void currentChatTest() {
        assertEquals(userDataGuard.userCurrentChat("TestUser"), null);
        userDataGuard.changeUserCurrentChat("TestUser", "helloworld");
        assertTrue(userDataGuard.userCurrentChat("TestUser").equals("helloworld"));
    }
}
