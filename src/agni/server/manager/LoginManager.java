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
        // TODO Auto-generated method stub
    	
    	// check database 
    	if (userDataGuard.authenticate(user,password)) 
    		infoSender.sendInfo(ip, "1");
    	else
    		infoSender.sendInfo(ip, "0");
    }

}
