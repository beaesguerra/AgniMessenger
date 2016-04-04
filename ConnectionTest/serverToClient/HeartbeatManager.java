package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.HeartbeatListener;
import agni.server.sender.HeartbeatSender;

public class HeartbeatManager implements HeartbeatListener{
    private HeartbeatSender heartbeatSender;
    private I_UserDataGuard userDataGuard; 
    private long lastTimeSent; 
    
    public HeartbeatManager(HeartbeatSender heartbeatSender, UserDataGuard userDataGuard) {
        this.heartbeatSender = heartbeatSender;
        this.userDataGuard = userDataGuard;
        lastTimeSent = System.currentTimeMillis();
    }

    @Override
    public void receivedHeartBeat(String ip, byte status) {
        String username = userDataGuard.getUsername(ip);
        userDataGuard.resetLastHeartbeat(username);
    }
    
    public void update() {
        // if (System.currentTimeMillis() - lastTimeSent > 200) {
        //     sendHeartbeats(); 
        //     lastTimeSent = System.currentTimeMillis(); 
        // }
        // float elapsedTime = System.currentTimeMillis() - lastTimeSent; 
        // lastTimeSent = System.currentTimeMillis();
        // for (String user : userDataGuard.getOnlineUsernames()) {
        //     userDataGuard.addToLastHeartbeat(user, elapsedTime); 
        //     if(userDataGuard.getLastHeartbeat(user) > 5000) {
        //         userDataGuard.changeUserCurrentIp(user, null);
        //     }
        // }
    }

    private void sendHeartbeats() {
        for (String userIp : userDataGuard.getOnlineUserIps()) {
            heartbeatSender.sendHeartbeat(userIp);
        }
    }
}
