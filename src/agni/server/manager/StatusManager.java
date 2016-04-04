package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.StatusListener;
import agni.server.sender.StatusSender;
import agni.server.sender.StatusSender.Status;

public class StatusManager implements StatusListener{
    private StatusSender statusSender;
    private I_UserDataGuard userDataGuard;

    public StatusManager(StatusSender statusSender,
                         UserDataGuard userDataGuard) {
        this.statusSender = statusSender;
        this.userDataGuard = userDataGuard;
    }

    @Override
    public void receiveStatusChange(String ip, byte status) {
        String username = userDataGuard.getUsername(ip); 
    	for (String friend : userDataGuard.getFriends(username)) {
    		if (status == Status.OFFLINE.bytes()) {
    			 statusSender.sendStatus(userDataGuard.userCurrentIp(friend), Status.OFFLINE, username);  
            }
            else if (status == Status.ONLINE.bytes()) { 
            	statusSender.sendStatus(userDataGuard.userCurrentIp(friend), Status.ONLINE, username);
            }
    	}
    }
}
