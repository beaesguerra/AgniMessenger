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
        if(type == 0x00) { 			// join chat; message = group to join
        	String username = userDataGuard.getUsername(ip); 
        	String groupName = message; 
        	if (userDataGuard.checkGroup(groupName)) {
        		userDataGuard.addUserToChat(username,groupName); 
        		infoSender.sendInfo(ip, "success: joining " + groupName);
        	}
        	else {
        		infoSender.sendInfo(ip, "failed: " + groupName + " doesn't exist");
        	}
        }
        else if (type == 0x01){ 	// leave chat 
        	String username = userDataGuard.getUsername(ip); 
        	String groupName = message; 
        	if (userDataGuard.checkGroup(groupName)) {
        		userDataGuard.removeUserFromChat(username,groupName); 
        		infoSender.sendInfo(ip, "success: leaving " + groupName);
        	}
        	else {
        		infoSender.sendInfo(ip, "failed: " + groupName + " doesn't exist");
        	}
        	
        }
        else if (type == 0x02){ 	// see friends list 
        	
        }
        else if (type == 0x03){		// check friend status 
        	
        }
        else if (type == 0x04){		// add friend 
        	
        }
        else if (type == 0x05){		// logout 
        	
        }
        
        	
    }
}
