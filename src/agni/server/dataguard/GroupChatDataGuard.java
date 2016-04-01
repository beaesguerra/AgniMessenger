package agni.server.dataguard;

public class GroupChatDataGuard implements I_GroupChatDataGuard {
//  private database;

    public GroupChatDataGuard(String dbname, String username, String password) {

    }

    public String[] allGroupChats() {
        return null;
    }

    public void createGroupChat(String owner, String groupChatName) {

    }

    /* Throw IllegalArgumentException if groupChat does not exist */
    public void deleteGroupChat(String groupChatName) {
        
    }

    public String owner(String groupChatName) {
        return null;
    }

    public String[] history(String groupChatName) {
        return null;
    }

    public void addMessage(String message, String sender, String groupname){

    }

    public String[] users(String groupChatName) {
        return null;
    }

    @Override
    public void changeUserCurrentChat(String user, String chatname) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String userCurrentChat(String user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addUserToChat(String username, String groupChatName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeUserFromChat(String username, String groupName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addUser(String user, String groupChatName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean chatExists(String groupName) {
        // TODO Auto-generated method stub
        return false;
    }
}
