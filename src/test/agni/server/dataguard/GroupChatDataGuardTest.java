package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.GroupChatDataGuard;

public class GroupChatDataGuardTest {

    private GroupChatDataGuard groupChatDataGuard;

    @Before
    public void setup() throws SQLException {
        String dbName = "agni_test";
        String dbUserName = "agni_tester";
        String source = "AgniTest.sql";
        String[] commands =  new String[]{"mysql", "--user=" + dbUserName, dbName,"-e", "source " + source};
        try {
			Process proc = Runtime.getRuntime().exec(commands);
			Thread.sleep(100);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        groupChatDataGuard = new GroupChatDataGuard("agni_test", "agni_tester", "");
    }
  
   @Test
    public void getGroupChatsTests() throws SQLException {
    	
        String[] expectedChats = {"TestChat", "AnotherChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();
       
        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

  @Test
    public void createGroupChatsTest() throws SQLException {
        groupChatDataGuard.createGroupChat("BillyBob", "NewTestChat");

        String[] expectedChats = {"TestChat", "AnotherChat", "NewTestChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();
        
        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

  @Test
    public void deleteGroupChatsTest() throws SQLException {
        groupChatDataGuard.deleteGroupChat("AnotherChat");

        String[] expectedChats = {"TestChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }

   @Test
    public void deleteNonExistingGroupChatsTest() throws SQLException {
        groupChatDataGuard.deleteGroupChat("NonExistingGroupChat");

        String[] expectedChats = {"TestChat", "AnotherChat"};
        String[] actualChats = groupChatDataGuard.allGroupChats();

        Arrays.sort(expectedChats);
        Arrays.sort(actualChats);

        assertArrayEquals(actualChats, expectedChats);
    }


   @Test
    public void getGroupChatOwnerTest() throws SQLException {
        assertTrue(groupChatDataGuard.owner("TestChat").equals("TestUser"));
    }

   //@Test
    public void getGroupChatHistoryTest() throws SQLException {
       // tested in addMessageTest()
        
        
    }

    @Test
    public void addMessageTest() throws SQLException {
        groupChatDataGuard.addMessage("hello", "TestUser", "TestChat");
        String [] expectedMessages = {"TestUser: hello"} ;
        String [] actualMessages = groupChatDataGuard.history("TestChat"); 
        assertArrayEquals(actualMessages, expectedMessages);
    }


   @Test
    public void getGroupChatUsersTest() throws SQLException {
        String [] actualGroupChatUsers = groupChatDataGuard.users("TestChat");
        String [] expectedGroupChatUsers = { "TestUser" };
        assertArrayEquals(actualGroupChatUsers, expectedGroupChatUsers);
    }

    @Test
    public void currentChatTest() throws SQLException {
    	String actualCurrentChat = groupChatDataGuard.userCurrentChat("TestUser");
    	String expectedCurrentChat = "TestChat"; 
    	assertEquals(actualCurrentChat, expectedCurrentChat);
    }
    @Test
    public void changeChatTest() throws SQLException {
    	groupChatDataGuard.createGroupChat("EnochTsang", "newGroup");
    	String actualCurrentChat = groupChatDataGuard.userCurrentChat("TestUser");
    	String expectedCurrentChat = "TestChat"; 
    	assertEquals(actualCurrentChat, expectedCurrentChat);
    	groupChatDataGuard.changeUserCurrentChat("TestUser", "newGroup");
    	actualCurrentChat = groupChatDataGuard.userCurrentChat("TestUser");
    	expectedCurrentChat = "newGroup"; 
    	assertEquals(actualCurrentChat, expectedCurrentChat);
    	
    }
   @Test
   public void chatExistsTest() throws SQLException {
	   boolean actual = groupChatDataGuard.chatExists("TestChat");
	   boolean expected = true;
	   assertEquals(expected, actual);
   }
   @Test
   public void chatDoesNotExistTest() throws SQLException {
	   boolean actual = groupChatDataGuard.chatExists("does not exist");
	   boolean expected = false;
	   assertEquals(expected, actual);
   }
   @Test 
   public void addUserToChat() throws SQLException {
	   groupChatDataGuard.addUserToChat("EnochTsang", "TestChat");
	   String actualChat = groupChatDataGuard.userCurrentChat("EnochTsang");
	   String expectedChat = "TestChat"; 
	   assertEquals(actualChat, expectedChat);
   }
}
