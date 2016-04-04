package agni.server.dataguard;

import java.sql.SQLException;

public interface I_GroupChatDataGuard {

    public String[] allGroupChats() throws SQLException;
    
    public void createGroupChat(String owner, String groupChatName) throws SQLException;

    /* Throw IllegalArgumentException if groupChat does not exist 
     * Should remove group, remove messages associated with it, and 
     * remove all tuples in GroupMembers table with the same groupChatName
     * */
    public void deleteGroupChat(String groupChatName) throws SQLException;

    public String owner(String groupChatName) throws SQLException;

    public String[] history(String groupChatName) throws SQLException;

    public void addMessage(String message, String sender, String groupname) throws SQLException;

    public String[] users(String groupChatName) throws SQLException;
    
    
    public void changeUserCurrentChat(String user, String chatname) throws SQLException;

    public String userCurrentChat(String user) throws SQLException;
    
    public void addUserToChat(String username, String groupName) throws SQLException;

    public void removeUserFromChat(String username, String groupName);

    public boolean chatExists(String groupName) throws SQLException;

	



}
