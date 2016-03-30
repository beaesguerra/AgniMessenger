package agni.server.dataguard;

public interface I_GroupChatDataGuard {

    public String[] allGroupChats();
    
    public void createGroupChat(String owner, String groupChatName);

    /* Throw IllegalArgumentException if groupChat does not exist */
    public void deleteGroupChat(String groupChatName);

    public String owner(String groupChatName);

    public String[] history(String groupChatName);

    public void addMessage(String message, String sender, String groupname);

    public String[] users(String groupChatName);

    public void addUser(String user, String groupChatName);

}
