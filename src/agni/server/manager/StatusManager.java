package agni.server.manager;

import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.StatusListener;
import agni.server.sender.StatusSender;

public class StatusManager implements StatusListener{
    private StatusSender statusSender;
    private UserDataGuard userDataGuard;

    public StatusManager(StatusSender statusSender,
                         UserDataGuard userDataGuard) {
        this.statusSender = statusSender;
        this.userDataGuard = userDataGuard;
    }

    @Override
    public void ReceivedHeartBeat(String ip, byte status) {
        // TODO Auto-generated method stub
        
    }
}
