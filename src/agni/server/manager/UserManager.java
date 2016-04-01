package agni.server.manager;

import java.util.Arrays;

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
    private boolean friendHasAccepted(String username, String friend) {
    	String [] friendList = userDataGuard.getFriends(friend); 
		if(Arrays.asList(friendList).contains(username)) {
			return true;
		}
		else {
			return false;
		}
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
        	String groupName = userDataGuard.userCurrentChat(username); 
        	if (!userDataGuard.userCurrentChat(username).equals(null)) {
        		userDataGuard.removeUserFromChat(username,groupName); 
        		infoSender.sendInfo(ip, "success: leaving " + groupName);
        	}
        	else {
        		infoSender.sendInfo(ip, "failed: no current chat open");
        	}
        	
        }
        else if (type == 0x02){ 	// see friends list 
        	String [] friendList = userDataGuard.getFriends(username); 
        	String friends = "";
        	for (int i = 0; i < friendList.length; i++) {
        		friends.concat(friendList[i] + " " );
        		if(userDataGuard.isOnline(friendList[i])) {
        			friends.concat("online" + " \n" );
        		}
        		else {
        			friends.concat("offline" + " \n" );
        		}
        	}
        	// TODO get status of each friend
        	//String statusList = userDataGuard.getStatusList(username); 
        	infoSender.sendInfo(ip, "friends:" + friends);
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
        		
        		String [] friendList = userDataGuard.getFriends(username); 
        		
        		if(Arrays.asList(friendList).contains(friend)) {
        			if (friendHasAccepted(username,friend)) {
        				infoSender.sendInfo(ip, friend + " already added");
        			}
        			else {
        				infoSender.sendInfo(ip, "pending friend request for " + friend);
        			}
        		}
        		else {
        			userDataGuard.addFriend(username, friend);
        			infoSender.sendInfo(ip, "sending friend request to " + friend);
        		}
        	}
        	else {
        		infoSender.sendInfo(ip,  friend + " doesn't exist");
        	}
        	
        }
        else if (type == 0x05){		// logout 
        	userDataGuard.changeUserCurrentIp(username, null);
        	infoSender.sendInfo(ip, "logged out");
        }
    }

	

}
