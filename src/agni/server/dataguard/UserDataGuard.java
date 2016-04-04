package agni.server.dataguard;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class UserDataGuard implements I_UserDataGuard {
	private Connection database;
	private ArrayList<UserInfo> userInfo = null; 
	
//  private database;
    // private users, ips, and lastHeartBeat for each online user

    public UserDataGuard(String dbname, String username, String password) throws SQLException {
		userInfo = new ArrayList<UserInfo>();
       	MysqlDataSource dataSource = new MysqlDataSource(); 
    	dataSource.setUser(username);
    	dataSource.setPassword(password); 
    	dataSource.setDatabaseName(dbname);
    	database = dataSource.getConnection(); 
    }

    public void registerUser(String username, String salt, String passwordHash) {
    	PreparedStatement stmt = null;
    	
    	try {
    		stmt = database.prepareStatement("INSERT into Users (username, salt, passwordHash) values (?, ?, ?);");
    		stmt.setString(1, username);
    		stmt.setString(2, salt);
    		stmt.setString(3, passwordHash);
    		stmt.executeUpdate();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public String salt(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	String result = null;
    	try {
    		stmt = database.createStatement();
			rs = stmt.executeQuery("SELECT salt FROM Users WHERE username = \""+user + "\"");
			rs.first();
			result = rs.getString(1);
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

    public String getPasswordHash(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	String result = null;
    	try {
    		stmt = database.createStatement();
			rs = stmt.executeQuery("SELECT passwordHash FROM Users WHERE username = \""+user+ "\"");
			rs.first();
			result = rs.getString(1);
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }

    public String[] getFriends(String user) {
    	ArrayList<String> resultList = new ArrayList<String>();
    	String result[] = null;
    	Statement stmtA = null;
    	Statement stmtB = null;
    	ResultSet rsA = null;
    	ResultSet rsB = null;
    	
    	try {
    		stmtA = database.createStatement();
			stmtB = database.createStatement();
			rsA = stmtA.executeQuery("SELECT id FROM Users WHERE username = \"" + user + "\"");
			rsA.first();
			rsB = stmtB.executeQuery("SELECT userIDTwo FROM Friends WHERE userIDOne =\""+rsA.getString(1)+"\"" );
			//rsB.first();
			//System.out.println(rsB.getString(1));
			
			while(rsB.next()) {
				resultList.add(rsB.getString(1));
			}

			rsA.close();
			stmtA.close();
			rsB.close();
			stmtB.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	result = new String[resultList.size()];
    	for(int i = 0; i < resultList.size(); i++) {
    		result[i] = resultList.get(i);
    	}
    	return result;
    }

    public void createFriendship(String user1, String user2) {
     	Statement stmtA = null;
    	Statement stmtB = null;
    	Statement stmtC = null;
    	ResultSet rsA = null;
    	ResultSet rsB = null;
    	ResultSet rsC = null;

    	try {
    		stmtA = database.createStatement();
			stmtB = database.createStatement();
			stmtC = database.createStatement();
			rsA = stmtA.executeQuery("SELECT id FROM Users WHERE username == "+user1);
			rsB = stmtB.executeQuery("SELECT id FROM Users WHERE username == "+user2);
			rsC = stmtC.executeQuery("INSERT INTO Friends (userIdOne, userIdTwo) values (" + rsA.getString(1) + ", " + rsB.getString(1) +")");
			rsA.close();
			stmtA.close();
			rsB.close();
			stmtB.close();
			rsC.close();
			stmtC.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /*
     * if ip == null, remove from data structure that keeps track of online users
     */
    public void changeUserCurrentIp(String user, String ip) {
    	for(int i = 0; i<userInfo.size(); i++){
    		if(userInfo.get(i).username.equals(user))
    			if(ip == null) {
        			userInfo.get(i).ip = null;	
    			}
    			else {
    			userInfo.get(i).ip = ip;
    			}
    	}
    }

    /* return null if user not online */
    public String userCurrentIp(String user) {
    	String currentIP = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).username.equals(user))
    			currentIP = userInfo.get(i).ip;
    	}
		return currentIP;
    }

    @Override
    public void loginUser(String ip, String user) {
    	if(ip == null || user == null)
    		throw new NullPointerException("loginUser received a null user/ip");
        userInfo.add(new UserInfo(user, 0, ip));
    }

    @Override
    public boolean userExists(String user) {
    	boolean userExists = false;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(user.equals(userInfo.get(i).username)) {
    			userExists = true;
    			break;
    		}
    	}
         return userExists;
    }
    @Override
    public String getUsername(String ip) {
    	if(ip == null)
    		throw new NullPointerException("getUsername received a null ip");
    	String userName = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(ip.equals(userInfo.get(i).ip)) {
    			userName = userInfo.get(i).username;
    		}
    	}
    	return userName;
    }

    
    @Override
    public boolean isOnline(String friend) {
    	boolean isOnline = false;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(friend.equals(userInfo.get(i).username)) {
    			if(userInfo.get(i).ip != null){
    				isOnline = true;
    				break;
    			}
    		}
    	}
    	return isOnline;
    }

    // set lastheartbeat for username to 0 
    @Override
    public void resetLastHeartbeat(String username) {
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(username.equals(user)Info.get(i).username) {
    			userInfo.get(i).lastHeartbeat = 0;
    		}
    	}
        
    }

    @Override
    public String[] getOnlineUsernames() {
    	ArrayList<String> onlineUsers = new ArrayList<String>();
    	String[] result = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).ip != null) {
    			onlineUsers.add(userInfo.get(i).username);
    		}
    	}
    	result = new String[onlineUsers.size()];
    	for(int i = 0; i < onlineUsers.size(); i++) {
    		result[i] = onlineUsers.get(i);
    	}
    	return result;
    }
    
    
    // add to a user's current lastheartbeat time
    @Override
    public void addToLastHeartbeat(String user, float elapsedTime) {
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).username.equals(user)) {
    			userInfo.get(i).lastHeartbeat += elapsedTime;
    			break;
    		}
    	}
    }
    // return a user's current lastheartbeat
    @Override
    public int getLastHeartbeat(String user) {
    	int lastHeartBeat = -1;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).username.equals(user)) {
    			lastHeartBeat = (int) userInfo.get(i).lastHeartbeat;
    			break;
    		}
    	}
    	return lastHeartBeat;
    }

    @Override
    public String[] getOnlineUserIps() {
    	ArrayList<String> onlineUsersIp = new ArrayList<String>();
    	String[] result = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).ip != null) {
    			onlineUsersIp.add(userInfo.get(i).ip);
    		}
    	} 
    	result = new String[onlineUsersIp.size()];
    	for(int i = 0; i < onlineUsersIp.size(); i++) {
    		result[i] = onlineUsersIp.get(i);
    	}
    	return result;
    }
    
    @Override
    public String getIp(String user) {
    	String userIp = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).username.equals(user)) {
    			userIp = userInfo.get(i).ip;
    			break;
    		}
    	}
    	return userIp;
    }
    

	public String[] usersOnline() {
    	ArrayList<String> onlineUsers = new ArrayList<String>();
    	String[] result = null;
    	for(int i = 0; i < userInfo.size(); i++) {
    		if(userInfo.get(i).ip != null) {
    			onlineUsers.add(userInfo.get(i).username);
    		}
    	}
       	result = new String[onlineUsers.size()];
    	for(int i = 0; i < onlineUsers.size(); i++) {
    		result[i] = onlineUsers.get(i);
    	}
    	return result;
	}	
    
    private class UserInfo {
    	protected String username = null;
    	protected float lastHeartbeat = 0;
    	protected String ip = null;
    	
    	protected UserInfo(String userName, float hb, String ip){
    		this.username = userName;
    		this.lastHeartbeat = hb;
    		this.ip = ip;
    	}
    }
}
