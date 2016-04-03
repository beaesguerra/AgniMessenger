package agni.server.dataguard;

import java.util.HashMap;

public class UserDataGuard implements I_UserDataGuard {
//  private database;
    HashMap<String,String> userIps; 

    public UserDataGuard(String dbname, String username, String password) {

    }

    public void registerUser(String username, String salt, String passwordHash) {

    }

    public String salt(String user) {
        return null;
    }

    public String getPasswordHash(String user) {
        return null;
    }

    public String[] getFriends(String user) {
        return null;
    }

    public void createFriendship(String user1, String user2) {

    }

    /*
     * if ip == null, remove from data structure that keeps track of online users
     */
    public void changeUserCurrentIp(String user, String ip) {

    }

    /* return null if user not online */
    public String userCurrentIp(String user) {
        return null;
    }

    @Override
    public void loginUser(String ip, String user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean userExists(String user) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public String getUsername(String ip) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public boolean isOnline(String friend) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetLastHeartbeat(String username) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String[] getOnlineUsernames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addToLastHeartbeat(String user, float elapsedTime) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getLastHeartbeat(String user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String[] getOnlineUserIps() {
        // TODO Auto-generated method stub
        return null;
    }
}
