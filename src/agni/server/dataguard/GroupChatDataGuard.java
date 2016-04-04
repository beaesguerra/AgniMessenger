package agni.server.dataguard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

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
    	String statement = "SELECT * FROM GroupChats";
    	ResultSet rs = stmt.executeQuery(statement);
    	System.out.println(statement);
    	ArrayList<String> groupChatsList = new ArrayList<String>(); 
    	while (rs.next()) {
    		groupChatsList.add(rs.getString("name"));
    	}
    	String [] groupChats = new String[groupChatsList.size()];
        groupChats = groupChatsList.toArray(groupChats);
    	return groupChats;
    }

    public void createGroupChat(String owner, String groupChatName) throws SQLException {
    	Statement stmt = conn.createStatement();
    	String statement = "SELECT id FROM Users WHERE username = '" +owner + "';";
    	System.out.println(statement);
    	ResultSet rs = stmt.executeQuery(statement);
    	String ownerId = null;
    	while (rs.next()) {
	    	ownerId = rs.getString("id");
    	}
    	statement = "INSERT INTO GroupChats VALUES('" + groupChatName + "', " + ownerId + ");";
    	int rs2 = stmt.executeUpdate(statement);
    	System.out.println(statement);
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
