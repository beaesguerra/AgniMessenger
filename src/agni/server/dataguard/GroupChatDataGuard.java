package agni.server.dataguard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GroupChatDataGuard implements I_GroupChatDataGuard {
	private Connection conn;

    public GroupChatDataGuard(String dbname, String username, String password) throws SQLException {
    	MysqlDataSource dataSource = new MysqlDataSource(); 
    	dataSource.setUser(username);
    	dataSource.setPassword(password); 
    	dataSource.setDatabaseName(dbname);
    	conn = dataSource.getConnection(); 
    	
    }

    public String[] allGroupChats() throws SQLException {
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("SELECT * FROM GroupChats");
    	ArrayList<String> groupChatsList = null; 
    	while (rs.next()) {
    		groupChatsList.add(rs.getString("name"));
    	}
    	String [] groupChats = new String[groupChatsList.size()];
        return groupChats;
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
