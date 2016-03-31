package agni.server.manager;

import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.LoginListener;
import agni.server.sender.InfoSender;

public class LoginManager implements LoginListener{
    private InfoSender infoSender;
    private I_UserDataGuard userDataGuard;

    public LoginManager(InfoSender infoSender,
                        UserDataGuard userDataGuard) {
        this.infoSender = infoSender;
        this.userDataGuard = userDataGuard;
    }

    @Override
    public void loginRequest(String ip, String user, String password) {
    	if (password == userDataGuard.getPassword(user)) 
    		infoSender.sendInfo(ip, "approved");
    	else
    		infoSender.sendInfo(ip, "declined");
    }

}
