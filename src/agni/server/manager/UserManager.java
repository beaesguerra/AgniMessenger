package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.UserListener;
import agni.server.sender.InfoSender;

public class UserManager implements UserListener{
    private InfoSender infoSender;
    private I_UserDataGuard userDataGuard;

    public UserManager(InfoSender infoSender,
                       UserDataGuard userDataGuard) {
        this.infoSender = infoSender;
        this.userDataGuard = userDataGuard;
    }

    @Override
    public void userRequest(String ip, byte type, String message) {
        // TODO Auto-generated method stub
    	String username = userDataGuard.getUsername(ip);
    	if(type == 0x00) { 			// join chat; message = group to join
        	String groupName = message; 
        	if (userDataGuard.groupExists(groupName)) {
        		if (!userDataGuard.userCurrentChat(username).equals(null)) {
        			userDataGuard.removeUserFromChat(username, userDataGuard.userCurrentChat(username));
        		}
        		userDataGuard.addUserToChat(username,groupName); 
        		infoSender.sendInfo(ip, "success: joining " + groupName);
        	}
        	else {
        		infoSender.sendInfo(ip, "failed: " + groupName + " doesn't exist");
        	}
        }
        else if (type == 0x01){ 	// leave chat 
        	String groupName = message; 
        	if (userDataGuard.groupExists(groupName)) {
        		userDataGuard.removeUserFromChat(username,groupName); 
        		infoSender.sendInfo(ip, "success: leaving " + groupName);
        	}
        	else {
        		infoSender.sendInfo(ip, "failed: " + groupName + " doesn't exist");
        	}
        	
        }
        else if (type == 0x02){ 	// see friends list 
        	String friendList = userDataGuard.getFriendList(username); 
        	// TODO get status of each friend
        	//String statusList = userDataGuard.getStatusList(username); 
        	infoSender.sendInfo(ip, "friends:" + friendList);
        }
        else if (type == 0x03){		// check friend status ; message = friend to check 
        	String friend = message;
        	if (userDataGuard.isOnline(friend)) {
        		infoSender.sendInfo(ip, friend + " online");
        	}
        	else {
        		infoSender.sendInfo(ip, friend + " offline");
        	}
        }
        else if (type == 0x04){		// add friend 
        	String friend = message;
        	if (userDataGuard.userExists(message)) {
        		
        		String friendList = userDataGuard.getFriendList(username); 
        		if(friendList.contains(friend)) {
        			infoSender.sendInfo(ip, friend + " already added");
        		}
        		else {
        			userDataGuard.addFriend(username, friend);
        			infoSender.sendInfo(ip, friend + " added");
        		}
        	}
        	else {
        		infoSender.sendInfo(ip,  friend + " doesn't exist");
        	}
        	
        }
        else if (type == 0x05){		// logout 
        	userDataGuard.logoutUser(username);
        	infoSender.sendInfo(ip, "logged out");
        	
        }
        
        	
    }

}
