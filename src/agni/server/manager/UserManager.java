package agni.server.manager;

import java.util.Arrays;

import agni.server.dataguard.GroupChatDataGuard;
import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.UserListener;
import agni.server.sender.InfoSender;

public class UserManager implements UserListener {
	private InfoSender infoSender;
	private I_UserDataGuard userDataGuard;
	private I_GroupChatDataGuard groupChatDataGuard;

	public enum UserRequestType {
		JOIN_CHAT((byte) 0x00), 
		LEAVE_CHAT((byte) 0x01), 
		FRIENDS_LIST((byte) 0x02), 
		USER_STATUS((byte) 0x03), 
		ADD_FRIEND((byte) 0x04), 
		LOGOUT((byte) 0x05), 
		CREATE_CHAT((byte) 0x06), 
		REMOVE_CHAT((byte) 0x07);

		private final byte bytes;

		private UserRequestType(byte bytes) {
			this.bytes = bytes;
		}

		public final byte bytes() {
			return bytes;
		}
	}

	public UserManager(InfoSender infoSender, UserDataGuard userDataGuard, GroupChatDataGuard groupChatDataGuard) {
		this.infoSender = infoSender;
		this.userDataGuard = userDataGuard;
		this.groupChatDataGuard = groupChatDataGuard;
	}

	private boolean friendHasAccepted(String username, String friend) {
		String[] friendList = userDataGuard.getFriends(friend);
		if (Arrays.asList(friendList).contains(username)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
    public void userRequest(String ip, byte type, String argument) {
        // TODO Auto-generated method stub
        String username = userDataGuard.getUsername(ip);
        if(type == UserRequestType.JOIN_CHAT.bytes()) {             // join chat; argument = group to join
            String groupName = argument; 
            if (groupChatDataGuard.chatExists(groupName)) {
                if (!groupChatDataGuard.userCurrentChat(username).equals(null)) {
                	groupChatDataGuard.removeUserFromChat(username, groupChatDataGuard.userCurrentChat(username));
                }
                groupChatDataGuard.addUserToChat(username,groupName); 
                infoSender.sendInfo(ip, "success: joining " + groupName);
            }
            else {
                infoSender.sendInfo(ip, "failed to join chat: " + groupName + " doesn't exist");
            }
        }
        else if (type == UserRequestType.LEAVE_CHAT.bytes()){   // leave chat 
            String groupName = groupChatDataGuard.userCurrentChat(username); 
            if (!groupChatDataGuard.userCurrentChat(username).equals(null)) {
            	groupChatDataGuard.removeUserFromChat(username,groupName); 
                infoSender.sendInfo(ip, "success: leaving " + groupName);
            }
            else {
                infoSender.sendInfo(ip, "failed to leave chat: not part of a chat");
            }
            
            
        }
        else if (type == UserRequestType.FRIENDS_LIST.bytes()){     // see friends list 
            String [] friendList = userDataGuard.getFriends(username); 
            String friends = "";
            if (friendList.length == 0) {
            	infoSender.sendInfo(ip, "friends:\nYou have no friends (Enoch)"); 
            	return;
            }
            for (int i = 0; i < friendList.length; i++) {
                friends.concat(friendList[i] + " " );
                if(userDataGuard.isOnline(friendList[i])) {
                    friends.concat("online\n" );
                }
                else {
                    friends.concat("offline\n" );
                }
            }
            infoSender.sendInfo(ip, "friends:\n" + friends);
        }
        else if (type == UserRequestType.USER_STATUS.bytes()){        // check friend status ; argument = friend to check 
            String friend = argument;
            if(userDataGuard.userExists(friend)) {
	            if (userDataGuard.isOnline(friend)) {
	                infoSender.sendInfo(ip, friend + " online");
	            }
	            else {
	                infoSender.sendInfo(ip, friend + " offline");
	            }
            }
            else {
            	infoSender.sendInfo(ip, friend + " does not exist");
            }
        }
        else if (type == UserRequestType.ADD_FRIEND.bytes()){       // add friend 
            String friend = argument;
            if (userDataGuard.userExists(argument)) {
                
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
                    userDataGuard.createFriendship(username, friend);
                    infoSender.sendInfo(ip, "sending friend request to " + friend);
                }
            }
            else {
                infoSender.sendInfo(ip,  friend + " doesn't exist");
            }
            
        }
        else if (type == UserRequestType.LOGOUT.bytes()){       // logout 
            userDataGuard.changeUserCurrentIp(username, null);
            infoSender.sendInfo(ip, "logged out");
        }
        else if (type == UserRequestType.CREATE_CHAT.bytes()){       // create chat 
        	String groupName = argument; 
        	if (!groupChatDataGuard.chatExists(groupName)){ 
        		groupChatDataGuard.createGroupChat(username, groupName); 	//create chat
        		if(!groupChatDataGuard.userCurrentChat(username).equals(null)) {
        			groupChatDataGuard.removeUserFromChat(username, groupChatDataGuard.userCurrentChat(username));
        		}
        		groupChatDataGuard.addUserToChat(username, groupName);
        		infoSender.sendInfo(ip, "creating chatroom " + groupName);	
        	}
        	else {
        		infoSender.sendInfo(ip, "chatroom already exists ");
        	}
        }
        else if (type == UserRequestType.REMOVE_CHAT.bytes()){       // remove chat 
           String groupName = argument;
           if (groupChatDataGuard.chatExists(groupName)){ 
        	   groupChatDataGuard.deleteGroupChat(groupName); 
        	   infoSender.sendInfo(ip,  "removing chatroom " + groupName);
           }
           else {
        	   infoSender.sendInfo(ip,  "chatroom " + groupName + " does not exist");
           }
        }
    }

    

}
