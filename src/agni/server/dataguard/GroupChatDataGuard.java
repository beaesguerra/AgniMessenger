package agni.server.dataguard;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GroupChatDataGuard implements I_GroupChatDataGuard {
	private Connection database;

    public GroupChatDataGuard(String dbname, String username, String password) throws SQLException {
    	MysqlDataSource dataSource = new MysqlDataSource(); 
    	dataSource.setUser("agni");
    	dataSource.setPassword(""); 
    	dataSource.setDatabaseName("agni");
    	database = dataSource.getConnection(); 
    	
    }

    public String[] allGroupChats() {
        return null;
    }

    public void createGroupChat(String owner, String groupChatName) {

    }

    /* Throw IllegalArgumentException if groupChat does not exist 
     * Should remove group, remove messages associated with it, and 
     * remove all tuples in GroupMembers table with the same groupChatName
     * */
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

    /*
     */
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
    public boolean chatExists(String groupName) {
        // TODO Auto-generated method stub
        return false;
    }
    /*
	 * should return name of chats available
	 */
	@Override
	public String[] availableChats() {
		// TODO Auto-generated method stub
		return null;
	}
}
