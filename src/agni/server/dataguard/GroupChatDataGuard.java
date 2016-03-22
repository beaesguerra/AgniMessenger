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

    public void addUser(String user, String groupChatName) {

    }
}
