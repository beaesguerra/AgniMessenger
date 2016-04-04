package agni.server.dataguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class UserDataGuard implements I_UserDataGuard {
	private Connection database;
	private 
	
//  private database;
    // private users, ips, and lastHeartBeat for each online user

    public UserDataGuard(String dbname, String username, String password) throws SQLException {
       	MysqlDataSource dataSource = new MysqlDataSource(); 
    	dataSource.setUser(username);
    	dataSource.setPassword(password); 
    	dataSource.setDatabaseName(dbname);
    	database = dataSource.getConnection(); 
    }

    public void registerUser(String username, String salt, String passwordHash) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("INSERT TO Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public String salt(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			rs = stmt.executeQuery("SELECT salt FROM Users WHERE username == "+user);
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    public String getPasswordHash(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			rs = stmt.executeQuery("SELECT passwordHash FROM Users WHERE username == "+user);
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    public String[] getFriends(String user) {
    	ArrayList<String> result = new ArrayList<String>();
    	Statement stmtA = null;
    	Statement stmtB = null;
    	Statement stmtC = null;
    	Statement stmtD = null;
    	ResultSet rsA = null;
    	ResultSet rsB = null;
    	ResultSet rsC = null;
    	ResultSet rsD = null;

    	try {
    		stmtA = database.createStatement();
			stmtB = database.createStatement();
			stmtC = database.createStatement();
			stmtD = database.createStatement();
			rsA = stmtB.executeQuery("SELECT id FROM Users WHERE username == "+user);
			rsB = stmtB.executeQuery("SELECT userIDTwo FROM Friends WHERE userIDOne == "+rsA.getString(1) );
			rsC = stmtC.executeQuery("SELECT userIDOne FROM Friends WHERE userIDTwo == "+rsA.getString(1) );
			rsD = stmtD.executeQuery("SELECT * FROM "+ rsB + "," + rsC + "WHERE userIDOne=" + rsB +".userIDTwo");
		
			while(rsB.next()) {
				result.add(rsB.getString(1));
			}
			while(rsC.next()){
				result.add(rsC.getString(1));
			}
			
				

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
        return null;
    }

    public void createFriendship(String user1, String user2) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /*
     * if ip == null, remove from data structure that keeps track of online users
     */
    public void changeUserCurrentIp(String user, String ip) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /* return null if user not online */
    public String userCurrentIp(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public void loginUser(String ip, String user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean userExists(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public String getUsername(String ip) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public boolean isOnline(String friend) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return false;
    }

    // set lastheartbeat for username to 0 
    @Override
    public void resetLastHeartbeat(String username) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        
    }

    @Override
    public String[] getOnlineUsernames() {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return null;
    }
    // add to a user's current lastheartbeat time
    @Override
    public void addToLastHeartbeat(String user, float elapsedTime) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        
    }
    // return a user's current lastheartbeat
    @Override
    public int getLastHeartbeat(String user) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String[] getOnlineUserIps() {ResultSet rs = null;
	Statement stmt = null;
	
	try {
		stmt = database.createStatement();
		stmt = database.createStatement();
		rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
		rs.close();
		stmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
    }
    @Override
    public String getIp(String member) {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TODO Auto-generated method stub
        return null;
    }
    @Override
	public String[] usersOnline() {
    	ResultSet rs = null;
    	Statement stmt = null;
    	
    	try {
    		stmt = database.createStatement();
			stmt = database.createStatement();
			rs = stmt.executeQuery("insert into Users (username, salt, passwordHash) values (" + username + ", " + salt + ", " + passwordHash + ")");
			rs.close();
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}
	

}
