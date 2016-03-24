package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.GroupChatDataGuard;

public class GroupChatDataGuardTest {

    private GroupChatDataGuard groupChatDataGuard;

    @Before
    public void setup() {
        String dbName = "agni_test";
        String dbUserName = "agni_tester";
        String source = "/media/enoch/C47ABD537ABD434A/School/CPSC441/AgniMessenger/AgniTest.sql";
        String[] commands =  new String[]{"mysql", "--user=" + dbUserName, dbName,"-e", "source " + source};
        try {
			Runtime.getRuntime().exec(commands);
		} catch (IOException e) {
			e.printStackTrace();
		}
        groupChatDataGuard = new GroupChatDataGuard("agni_test", "agni_tester", "");
    }

    @Test
    public void getGroupChatsTests() {
        String[] expectedChats = {"TestChat", "AnotherChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

    @Test
    public void createGroupChatsTest() {
        groupChatDataGuard.createGroupChat("BillyBob", "NewTestChat");

        String[] expectedChats = {"TestChat", "AnotherChat", "NewTestChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

    @Test
    public void deleteGroupChatsTest() {
        groupChatDataGuard.deleteGroupChat("AnotherChat");

        String[] expectedChats = {"TestChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

    @Test
    public void deleteNonExistingGroupChatsTest() {
        groupChatDataGuard.deleteGroupChat("NonExistingGroupChat");

        String[] expectedChats = {"TestChat", "AnotherChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }


    @Test
    public void getGroupChatOwnerTest() {
        assertTrue(groupChatDataGuard.owner("TestChat").equals("TestUser"));
    }

    @Test
    public void getGroupChatHistoryTest() {
        fail("Not yet implemented");
    }

    @Test
    public void addMessageTest() {
        fail("Not yet implemented");
    }


    @Test
    public void getGroupChatUsersTest() {
        fail("Not yet implemented");
    }

    @Test
    public void addUserTest() {
        fail("Not yet implemented");
    }
}
