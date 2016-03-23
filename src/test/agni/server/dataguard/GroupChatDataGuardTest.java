package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.GroupChatDataGuard;

public class GroupChatDataGuardTest {

    private GroupChatDataGuard groupChatDataGuard;

    @Before
    public void setup() {
        groupChatDataGuard = new GroupChatDataGuard("agni_test", "test", "");
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
